package com.example.schoolmanangement.util.jwt;


import com.example.schoolmanangement.util.jwt.error.InvalidJwtError;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class JwtUtil {
    public static final Map<String, Object> RS256_TOKEN_HEADER =
            Map.ofEntries(Map.entry("typ", "JWT"), Map.entry("alg", "RS256"));
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String RSA_ALGORITHM = "RSA";

    @NonNull
    public static final PublicKey extractRsaPublicKey(@NonNull final String publicKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        final String extractedPublicKey = extractPublicKey(publicKey);
        final X509EncodedKeySpec encodedKeySpec =
                new X509EncodedKeySpec(Base64.getDecoder().decode(extractedPublicKey));
        final KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return keyFactory.generatePublic(encodedKeySpec);
    }

    @NonNull
    public static final PrivateKey extractRsaPrivateKey(@NonNull final String privateKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        final String extractedPrivateKey = extractPrivateKey(privateKey);
        final PKCS8EncodedKeySpec encodedKeySpec =
                new PKCS8EncodedKeySpec(Base64.getDecoder().decode(extractedPrivateKey));
        final KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return keyFactory.generatePrivate(encodedKeySpec);
    }

    @NonNull
    public static Claims extractAllClaims(
            @NonNull final String jwt, @NonNull final PublicKey publickey) {
        final JwtParser jwtParser;
        try {
            jwtParser =
                    Jwts.parserBuilder()
                            .setSigningKeyResolver(
                                    new SigningKeyResolverAdapter() {
                                        @Override
                                        public Key resolveSigningKey(
                                                final JwsHeader header, final Claims claims) {
                                            return publickey;
                                        }
                                    })
                            .build();
        } catch (JwtException | UnsupportedOperationException | IllegalArgumentException e) {
            log.info("Jwt error happened", e);
            throw new InvalidJwtError();
        }

        try {
            return jwtParser.parseClaimsJws(jwt).getBody();
        } catch (ExpiredJwtException
                | UnsupportedJwtException
                | MalformedJwtException
                | SignatureException
                | IllegalArgumentException
                | DecodingException e) {
            log.info("Jwt error happened", e);
            throw new InvalidJwtError();
        } catch (Exception e) {
            log.error("This error should not be raised.", e);
            throw new InvalidJwtError();
        }
    }

    @NonNull
    private static String extractPublicKey(@NonNull final String publicKey) {
        return publicKey
                .replaceAll("\\n", "")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "");
    }

    @NonNull
    private static String extractPrivateKey(@NonNull final String privateKey) {
        return privateKey
                .replaceAll("\\n", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "");
    }

    @NonNull
    public static String generateAccessToken(
            @NonNull final Map<String, Object> header,
            @NonNull final String issuer,
            @NonNull final Claims claims,
            @NonNull final String subject,
            @NonNull final ZonedDateTime issuedAt,
            @NonNull final ZonedDateTime expiration,
            @NonNull final PrivateKey privateKey) {
        return Jwts.builder()
                .setHeader(header)
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(issuedAt.toInstant()))
                .setExpiration(Date.from(expiration.toInstant()))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }
}
