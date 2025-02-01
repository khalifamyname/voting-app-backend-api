package tz.co.kishada.votingApp.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Khalifa K. Twaha
 * @email khalifa.twaha@ega.go.tz
 * @since 2/1/25
 */

@Entity
@Table(name = "votes")
@Getter
@Setter
public class Vote extends BaseEntity {

    @Transient
    private String selectedOpinion;

    @Transient
    private String voterName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "poll_id", referencedColumnName = "id")
    private Poll poll;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pollOption_id", referencedColumnName = "id")
    private PollOption pollOption;

    @Column(nullable = false, unique = true)
    private Long voterId;

}
