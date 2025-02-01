package tz.co.kishada.votingApp.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;
import tz.co.kishada.votingApp.enums.PollStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Khalifa K. Twaha
 * @email khalifa.twaha@ega.go.tz
 * @since 1/20/25
 */

@Entity
@Table(name = "polls")
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Poll extends BaseEntity {

    @Basic(optional = false)
    private String question;

    @Basic(optional = true)
    @Column(unique = true)
    private String pollNumber;

    @Enumerated(EnumType.STRING)
    private PollStatus status;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PollOption> options = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Vote> votes = new ArrayList<>();

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    private Boolean published = false;
}
