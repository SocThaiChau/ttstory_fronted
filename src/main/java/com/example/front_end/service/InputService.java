package com.example.front_end.service;

import org.springframework.stereotype.Service;

@Service
public class InputService {
    public static boolean containsAllowedCharacters(String input) {
        // Biểu thức chính quy để so khớp với chuỗi chỉ chứa các ký tự được phép
        String pattern = "^[a-zA-Z0-9@()\\s.]+$";

        // Kiểm tra so khớp
        return input.matches(pattern);
    }

    public static boolean containsUTF8(String input) {
        // Biểu thức chính quy để so khớp với chuỗi chỉ chứa các ký tự được phép
        String pattern = "[\\p{L}@(),.!\\s]+$";

        // Kiểm tra so khớp
        return input.matches(pattern);
    }

    public static boolean isStringLengthLessThan50(String input) {
        return input.length() < 50;
    }


    public boolean isValidInput(String input) {
        return input.matches("^[\\p{L}a-zA-Z0-9@(),_./!\\s]{1,100}$");
    }

    public static boolean isValidPassword(String input) {
        String pattern = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=]).{8,}$";
        return input.matches(pattern);
    }

    public boolean isInteger(int input) {
        try {
            // Attempt to parse the string as an integer
            Integer.parseInt(String.valueOf(input));
            return true;  // If successful, it's an integer
        } catch (NumberFormatException e) {
            return false; // If an exception is caught, it's not an integer
        }
    }

}
