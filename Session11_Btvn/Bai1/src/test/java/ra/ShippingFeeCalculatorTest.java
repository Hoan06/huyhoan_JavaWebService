package ra;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class ShippingFeeCalculatorTest {

    private ShippingFeeCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new ShippingFeeCalculator();
    }

    @Test
    void shouldCalculateFee_whenWeightIsUnder1kgAndDistanceUnder10km() {
        double fee = calculator.calculateFee(0.8, 9.0);
        assertThat(fee).isEqualTo(50000.0);
    }

    @Test
    void shouldCalculateFee_whenWeightIsIntegerAndDistanceBetween10And50() {
        double fee = calculator.calculateFee(2.0, 20.0);

        assertThat(fee).isEqualTo(110000.0);
    }

    @Test
    void shouldCalculateFee_whenWeightIsFractionAndDistanceIsOver50() {
        double fee = calculator.calculateFee(1.5, 55.0);

        assertThat(fee).isEqualTo(280000.0);
    }

    @Test
    void shouldCalculateFee_atDistanceBoundaries() {
        double feeAt10 = calculator.calculateFee(1.0, 10.0);
        assertThat(feeAt10).isEqualTo(50000.0);

        double feeAt50 = calculator.calculateFee(1.0, 50.0);
        assertThat(feeAt50).isEqualTo(250000.0);
    }

    @Test
    void shouldThrowException_whenInputsAreInvalid() {
        assertThatThrownBy(() -> calculator.calculateFee(0, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Weight and distance must be positive");

        assertThatThrownBy(() -> calculator.calculateFee(2, -5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Weight and distance must be positive");
    }
}