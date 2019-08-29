package eureka.eureka_client.validator;

import eureka.eureka_client.entity.DomainData;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;

/**
 * Created by tangminyan on 2019/7/12.
 */
public class CsvItemProcessor extends ValidatingItemProcessor<DomainData> {
    @Override
    public DomainData process(DomainData item) throws ValidationException {
        super.process(item);
        if (item.getType().equals("网页加速")) {
            item.setType("2");
        } else {
            item.setType("1");
        }
        return item;
    }
}










