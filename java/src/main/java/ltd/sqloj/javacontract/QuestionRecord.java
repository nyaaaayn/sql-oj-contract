package ltd.sqloj.javacontract;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@DataType
@Data
@EqualsAndHashCode
public class QuestionRecord {
    //问题id
    @Property
    private final int qid;
    //问题难度
    @Property
    private final int qdifficulty;
}
