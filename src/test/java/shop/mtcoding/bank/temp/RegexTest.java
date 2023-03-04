package shop.mtcoding.bank.temp;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

// java.util.regex.Pattern
public class RegexTest {
    
    @Test
    public void only_hangul() throws Exception {
        String value = "한글은위대해";
        boolean result = Pattern.matches("^[ㄱ-ㅎ가-힣]+$", value);
        System.out.println("Test only hangul: " + result);
        // String input = "한글";
        // String regex = "^[가-힣]*$";
        // System.out.println(input.matches(regex));
    }

    @Test
    public void only_english() throws Exception {
        String value = "abc";
        boolean result = Pattern.matches("^[a-zA-Z]+$", value);
        System.out.println("Test only english: " + result);
    }

    @Test
    public void not_hangul() throws Exception {
        String value = "english";
        boolean result = Pattern.matches("^[^ㄱ-ㅎ가-힣]*$", value);
        System.out.println("Test not hangul: " + result);
    }

    @Test
    public void not_english() throws Exception {
        String value = "english";
        boolean result = Pattern.matches("^[^a-zA-Z]*$", value);
        System.out.println("Test not english: " + result);
    }

    @Test
    public void only_number_and_english() throws Exception {
        String value = "123abc!";
        boolean result = Pattern.matches("^[a-zA-Z0-9]+$", value);
        System.out.println("Test only number and english: " + result);
    }

    @Test
    public void only_english_and_length_limit_2to10() throws Exception {
        String value = "abc";
        boolean result = Pattern.matches("^[a-zA-Z0-9]{2,10}$", value);
        System.out.println("Test only english and length limit 2to10: " + result);
    }

    // username, email, fullname
    @Test
    public void username_test() throws Exception {
        String username = "ssar";
        boolean result = Pattern.matches("^[a-zA-Z0-9]{2,20}$", username);
        System.out.println("Test username: " + result);
    }

    @Test
    public void fullname_test() throws Exception {
        String email = "쌀쌀이";
        boolean result = Pattern.matches("^[a-zA-Z가-힣]{1,20}$", email);
        System.out.println("Test fullname: " + result);
    }

    @Test
    public void email_test() throws Exception {
        String email = "tset@test.co.kr";
        boolean result = Pattern.matches("^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z\\.]{2,5}$", email); // .는 \\.로 해야함
        System.out.println("Test email: " + result);
    }
}
