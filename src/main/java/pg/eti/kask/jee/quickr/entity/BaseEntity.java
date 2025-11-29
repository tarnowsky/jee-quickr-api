package pg.eti.kask.jee.quickr.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public abstract class BaseEntity implements Serializable {

    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Version
    private Long version;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @Column(name = "modification_date_time")
    private LocalDateTime modificationDateTime;

    @PrePersist
    public void updateCreationDateTime() {
        creationDateTime = LocalDateTime.now();
    }

    @PreUpdate
    public void updateModificationDateTime() {
        modificationDateTime = LocalDateTime.now();
    }
}
