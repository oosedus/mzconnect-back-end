package likelion.MZConnent.repository.manner;

import likelion.MZConnent.domain.manner.Manner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MannerRepository extends JpaRepository<Manner, Long> {
}
