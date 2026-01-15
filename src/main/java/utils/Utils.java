package utils;

import config.Constants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

public class Utils {

    private Utils() {

    }

    public static final Cookie obterCookieCarrrinhoCompras(HttpServletRequest request) {
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            if (cookies[i].getName().equals(Constants.COOKIE_CARRINHO_COMPRAS_CHAVE)) {
                cookie = cookies[i];
                break;
            }
        }
        return cookie;
    }

    public static final String formatarMoeda(double valor) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(valor);
    }

    public static String gerarMD5(String senha) throws Exception {
        String senhaCriptografada = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] hashInBytes = messageDigest.digest(senha.getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashInBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            senhaCriptografada = stringBuilder.toString();
            if (senhaCriptografada == null || senhaCriptografada.trim().length() != 32) {
                throw new Exception("Falha ao criar MD5 para essa senha");
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new Exception("Falha ao criar MD5 para essa senha");
        }
        return senhaCriptografada;
    }


}
