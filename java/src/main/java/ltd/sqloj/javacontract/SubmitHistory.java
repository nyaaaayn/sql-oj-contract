package ltd.sqloj.javacontract;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@DataType
@Data
@EqualsAndHashCode
public class SubmitHistory {
    //竞赛id
    @Property
    private final int ctid;
    //提交记录id
    @Property
    private final int shid;
    //提交学生学工号
    @Property
    private final String susername;
    //问题id
    @Property
    private final int qid;
    //提交时间
    @Property
    private final String stime;
    //判题时间
    @Property
    private final String rtime;
    //是否通过
    @Property
    private final int accept;
    //做题使用时间
    @Property
    private final int usetime;
}
