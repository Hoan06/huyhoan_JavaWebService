package ra.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public class BookingRequest {
    @NotBlank(message = "Mã số chuyến bay không được để trống")
    private String flightNumber;

    @NotBlank(message = "Tên hành khách không được để trống hoặc chỉ chứa khoảng trắng")
    private String passengerName;

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }
}