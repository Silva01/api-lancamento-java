package br.net.silva.daniel.utils;

import br.net.silva.daniel.shared.business.utils.ValidateUtils;

import java.util.HashSet;
import java.util.Set;

public class AccountUtils {

    public static void validatePassword(String password) {
        ValidateUtils.isTextNotNullAndNotEmpty(password, "Password is required");
        ValidateUtils.isEqualsZero(password.length(), "Password must be greater than 6 Characters");
        validateRepeatedNumberPassword(password);
        validateIfPasswordOnlyNumbers(password);
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be greater than 6");
        }
    }

    private static void validateRepeatedNumberPassword(String password) {
        Set<Character> seenCharacters = new HashSet<>();
        for (char character : password.toCharArray()) {
            if (Character.isDigit(character) && !seenCharacters.add(character)) {
                throw new IllegalArgumentException("Password cannot have repeated numbers");
            }
        }
    }

    private static void validateIfPasswordOnlyNumbers(String password) {
        var isOnlyNumbers =  password.chars().allMatch(Character::isDigit);

        if (!isOnlyNumbers) {
            throw new IllegalArgumentException("Password cannot have only numbers");
        }
    }
}
