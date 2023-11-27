package br.net.silva.daniel;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CreditCardUtils {

    private static final List<Integer> MASTER_CARD_RANGE = List.of(51, 52, 53, 54, 55);

    public static String generateCreditCardNumber() {
        var ccNumber = new StringBuilder();
        boolean trava;

        do {
            trava = isValidGenerateNumber(generateRandomNumber(), ccNumber);
        } while (!trava);

        return ccNumber.toString();
    }

    private static boolean isValidGenerateNumber(Long number, StringBuilder ccNumber) {
        List<Integer> masterCardRange = Arrays.asList(51, 52, 53, 54, 55);
        for (Integer range : masterCardRange) {
            String numberString = String.format("%d%d", range, number);
            if (isValidLuhn(numberString)) {
                ccNumber.append(numberString);
                return true;
            }
        }

        return false;
    }

    public static boolean isValidLuhn(String number) {
        int checksum = Character.getNumericValue(number.charAt(number.length() - 1));
        int total = 0;

        for (int i = number.length() - 2; i >= 0; i--) {
            int sum = 0;
            int digit = Character.getNumericValue(number.charAt(i));
            if (i % 2 == 0) {
                digit *= 2;
            }

            sum = digit / 10 + digit % 10;
            total += sum;
        }

        return 10 - total % 10 == checksum;
    }

    public static long generateRandomNumber() {
        // Gera um número aleatório de 13 dígitos
        Random random = new Random();
        long randomNumber = 1000000000000L + ((long) random.nextInt(9000000) * 1000000L) + random.nextInt(1000000);

        // Calcula o dígito verificador usando o algoritmo de Luhn
        int checkDigit = calculateLuhnCheckDigit(randomNumber);

        // Combina o número original com o dígito verificador
        long result = randomNumber * 10 + checkDigit;

        return result;
    }

    private static int calculateLuhnCheckDigit(long baseNumber) {
        // Inicializa a soma total
        int totalSum = 0;

        // Percorre os dígitos da direita para a esquerda
        boolean doubleDigit = false;
        while (baseNumber > 0) {
            long digit = baseNumber % 10;
            baseNumber /= 10;

            if (doubleDigit) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }

            totalSum += digit;
            doubleDigit = !doubleDigit;
        }

        // Calcula o dígito verificador
        int checkDigit = (10 - (totalSum % 10)) % 10;

        return checkDigit;
    }
}
