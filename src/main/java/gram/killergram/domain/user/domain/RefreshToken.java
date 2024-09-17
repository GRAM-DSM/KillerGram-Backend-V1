package gram.killergram.domain.user.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "RefreshToken")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RefreshToken {

    @Id
    @NotBlank
    private String accountId;

    @Indexed
    @NotBlank
    private String token;

    @TimeToLive
    private long ttl;

    public void updateToken(String newToken, long ttl) {
        this.token = newToken;
        this.ttl = ttl;
    }
}
