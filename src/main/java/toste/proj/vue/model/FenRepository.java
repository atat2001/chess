package toste.proj.vue.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toste.proj.vue.dto.Fen;
@Repository
public interface FenRepository extends JpaRepository<Fen, Integer> {
}