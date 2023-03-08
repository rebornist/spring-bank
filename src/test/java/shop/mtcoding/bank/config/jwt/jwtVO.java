package shop.mtcoding.bank.config.jwt;

public interface jwtVO {
  public static final String SECRET = "mtcoding"; // HS256 암호화 키
  public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 1주일
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER = "Authorization";
}
