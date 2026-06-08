package ra.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ra.model.dto.request.AvailableTicketDto;
import ra.model.dto.request.CheckoutRequest;
import ra.model.dto.request.PartnerQrRequest;
import ra.model.dto.response.PartnerQrResponse;
import ra.model.entity.BookingItem;
import ra.model.entity.BookingOrder;
import ra.model.entity.FanAccount;
import ra.model.entity.TicketCategory;
import ra.repository.*;
import ra.security.QrGeneratorClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final TicketCategoryRepository ticketCategoryRepository;
    private final BookingOrderRepository bookingOrderRepository;
    private final BookingItemRepository bookingItemRepository;
    private final FanAccountRepository fanAccountRepository;
    private final QrGeneratorClient qrGeneratorClient;

    public Map<Long, List<AvailableTicketDto>> getAvailableTicketsGroupedByConcert() {
        List<TicketCategory> allCategories = ticketCategoryRepository.findAll();

        return allCategories.stream()
                .filter(category -> category.getRemainingQuantity() > 0)
                .map(cat -> new AvailableTicketDto(cat.getId(), cat.getName(), cat.getPrice(), cat.getRemainingQuantity()))
                .collect(Collectors.groupingBy(
                        dto -> ticketCategoryRepository.findById(dto.getTicketCategoryId()).get().getConcertId()
                ));
    }

    @Transactional
    public BookingOrder checkout(CheckoutRequest request, String fanEmail) {
        FanAccount fan = fanAccountRepository.findByEmail(fanEmail)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản người mua"));

        double totalAmount = request.getItems().stream()
                .mapToDouble(item -> {
                    TicketCategory cat = ticketCategoryRepository.findById(item.getTicketCategoryId())
                            .orElseThrow(() -> new RuntimeException("Hạng vé không tồn tại"));
                    if (cat.getRemainingQuantity() < item.getQuantity()) {
                        throw new RuntimeException("Hạng vé " + cat.getName() + " không đủ số lượng tồn kho");
                    }
                    return cat.getPrice() * item.getQuantity();
                })
                .sum();

        BookingOrder order = BookingOrder.builder()
                .fanId(fan.getId())
                .totalAmount(totalAmount)
                .status("PENDING")
                .build();
        final BookingOrder savedOrder = bookingOrderRepository.save(order);

        request.getItems().stream().forEach(item -> {
            TicketCategory cat = ticketCategoryRepository.findById(item.getTicketCategoryId()).get();

            cat.setRemainingQuantity(cat.getRemainingQuantity() - item.getQuantity());
            ticketCategoryRepository.save(cat);

            BookingItem bookingItem = BookingItem.builder()
                    .orderId(savedOrder.getId())
                    .ticketCategoryId(cat.getId())
                    .quantity(item.getQuantity())
                    .subTotal(cat.getPrice() * item.getQuantity())
                    .build();
            bookingItemRepository.save(bookingItem);
        });

        PartnerQrRequest partnerRequest = new PartnerQrRequest();
        partnerRequest.setOrderId(savedOrder.getId());
        partnerRequest.setFanEmail(fan.getEmail());

        try {
            PartnerQrResponse partnerResponse = qrGeneratorClient.generateQrCode(partnerRequest);
            savedOrder.setQrCodeUrl(partnerResponse.getQrCodeUrl());
            savedOrder.setStatus("SUCCESS");
        } catch (Exception e) {
            savedOrder.setQrCodeUrl(null);
            savedOrder.setStatus("PENDING_QR");
        }

        return bookingOrderRepository.save(savedOrder);
    }
}