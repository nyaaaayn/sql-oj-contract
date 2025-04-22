package ltd.sqloj.javacontract;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@DataType
@Data
@EqualsAndHashCode
public class StudentRecord {
    //学生学工号
    @Property
    private final String suid;
    //学生的参与的竞赛记录
    @Property
    private JSONObject ctidList;
    //学生自主做题的问题记录
    @Property
    private JSONObject qidList;
    //学生提交历史记录ID
    @Property
    private JSONArray shidList;
}
