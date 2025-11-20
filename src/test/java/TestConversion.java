import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestConversion {
    Conversion c = new Conversion();

    @Test
    void celsiusToFahrenheitZero(){
        Assertions.assertEquals(
                32,
                c.celsiusToFahrenheit(0),
                "Al convertir 0° celsius a fahrenheit nos da como resultado 32°");
    }

    @Test
    void  celsiusToFahrenheitCuarenta(){
        Assertions.assertEquals(
                -40,
                c.celsiusToFahrenheit(-40),
                "Al convertir -40° celsius a fahrenheit nos da como resultado 40°");
    }

    @Test
    void celsiusToFahrenheitCien(){
        Assertions.assertEquals(
                212,
                c.celsiusToFahrenheit(100),
                "Al convertir 100° celsius a fahrenheit nos da como resultado 212°");
    }

    @Test
    void fahrenheitToCelsiusTreintaYDos(){
        Assertions.assertEquals(
                0,
                c.fahrenheitToCelsius(32),
                "Al convertir 32° fahrenheit a celsius nos da como resultado 0°");
    }

    @Test
    void fahrenheitToCelsiusCuarenta(){
        Assertions.assertEquals(
                -40,
                c.fahrenheitToCelsius(-40),
                "Al convertir -40° fahrenheit a celsius nos da como resultado -42°");
    }

    @Test
    void fahrenheitToCelsiusDos(){
        Assertions.assertEquals(
                100,
                c.fahrenheitToCelsius(212),
                "Al convertir 212° fahrenheit a celsius nos da como resultado 100°");
    }
}
