package io.github.qifan777.knowledge.graph;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CompanyRepository extends Neo4jRepository<Company, String> {
}
