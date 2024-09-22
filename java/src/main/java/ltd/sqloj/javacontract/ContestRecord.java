package ltd.sqloj.javacontract;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import java.util.List;

@DataType
@Data
@EqualsAndHashCode
public class ContestRecord {

    @Property
    private final int ctid;

    @Property
    private final int cttype;

    @Property
    private final List<Integer> qidList;

    @Property
    private final String start;

    @Property
    private final String end;
}
