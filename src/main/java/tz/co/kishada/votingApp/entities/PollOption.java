package tz.co.kishada.votingApp.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Khalifa K. Twaha
 * @email khalifa.twaha@ega.go.tz
 * @since 1/20/25
 */

@Entity
@Table(name = "poll_options")
@NoArgsConstructor
@ToString
@Getter
@Setter
public class PollOption extends BaseEntity {

    @Basic(optional = false)
    @Column(name = "title", unique = true)
    private String title;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "poll_id", referencedColumnName = "id")
    private Poll poll;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Vote> votes = new ArrayList<>();

    @Transient
    String pollUuid;
}
