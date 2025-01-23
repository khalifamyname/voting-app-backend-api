package tz.co.kishada.votingApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import tz.co.kishada.votingApp.utils.CustomGeneratedData;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Setter
@Getter
@NoArgsConstructor
public class BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "uuid", nullable = true, unique = true)
	private String uuid = CustomGeneratedData.GenerateUniqueID();

	@JsonIgnore
	@Basic(optional = false)
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@JsonIgnore
	@Column(name = "updated_at")
	private LocalDateTime updatedAt = LocalDateTime.now();

	@JsonIgnore
	@Basic(optional = true)
	@Column(name = "created_by")
	private Long createdBy;

	@JsonIgnore
	@Column(name = "updated_by")
	private Long updatedBy;

	@Basic(optional = false)
	@Where(clause = "deleted = false")
	@Column(name = "deleted")
	private Boolean deleted = false;

	@Basic(optional = false)
	@Column(name = "active")
	@Where(clause = "active = true")
	private Boolean active = true;
	
}
