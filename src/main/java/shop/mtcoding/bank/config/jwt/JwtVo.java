package shop.mtcoding.bank.config.jwt;

/*
 * SECRET 노출되면 안된다. (클라우드AWS - 환경변수, 파일에 있는 것을 읽을수 있도록 설정)
 * 래프래시 토큰은 현재 구현하지 않음
 */
public interface JwtVo {
    public static final String SECRET = "매타코딩"; // HS256
    public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7일
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
}
