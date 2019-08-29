package eureka.eureka_client.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by tangminyan on 2019/7/12.
 */
@Entity
@Getter
@Setter
public class DomainData {

    @Id
    @GeneratedValue
    private Long id;

    private String enterName;

    private String domain;

    private String type;
}
