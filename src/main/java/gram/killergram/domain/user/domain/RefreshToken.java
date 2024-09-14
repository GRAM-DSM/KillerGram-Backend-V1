package gram.killergram.domain.user.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "RefreshToken",timeToLive = 60 * 60 * 2)
@Getter
@Setter
public class RefreshToken {
    @Id
    private String user_id;

    @Indexed
    @NotBlank
    private String token;
}
