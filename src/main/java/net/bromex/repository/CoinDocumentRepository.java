package net.bromex.repository;

import net.bromex.model.dto.Coin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoinDocumentRepository extends MongoRepository<Coin, String> {

    public List<Coin> findByName(final String name);

}
