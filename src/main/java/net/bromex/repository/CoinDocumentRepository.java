package net.bromex.repository;

import net.bromex.model.dto.Coin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CoinDocumentRepository extends MongoRepository<Coin, String> {


}
