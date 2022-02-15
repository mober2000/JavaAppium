import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    @Test
    public void testGetLocalNumber() {
        Assert.assertEquals("Метод getLocalNumber не возвращает число 14", 14, MainClass.getLocalNumber());
    }

    @Test
    public void testGetClassNumber() {
        Assert.assertTrue("Метод getClassNumber возвращает число, которое меньше либо равно 45", MainClass.getClassNumber() > 45);
    }

    @Test
    public void testGetClassString() {
        String testString = MainClass.getClassString();
        Assert.assertTrue("Метод getClassString не возвращает строку, в которой есть подстрока “hello” или “Hello”", testString.contains("Hello")|| testString.contains("hello"));
    }

}
