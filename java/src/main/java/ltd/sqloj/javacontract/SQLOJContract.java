package ltd.sqloj.javacontract;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Contract(name = "basic", info = @Info(version = "1.11"))
@Default
public class SQLOJContract implements ContractInterface {
    /**
     * 根据题目id查询题目是否存在
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean questionExists(final Context ctx, final String id) {

        ChaincodeStub stub = ctx.getStub();
        String content = stub.getStringState("question_record_" + id);

        return (content != null && !content.isEmpty());
    }

    /**
     * 根据题目id查询存储的题目内容
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public QuestionRecord readQuestionRecordByQid(final Context ctx, final String id) {
        ChaincodeStub stub = ctx.getStub();
        String stringState = stub.getStringState("question_record_" + id);

        if (stringState == null || stringState.isEmpty()) {
            String errorMessage = String.format("题目 %s 不存在", "question_record_" + id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage);
        }
        return JSON.parseObject(stringState, QuestionRecord.class);

    }

    /**
     * 创建题目
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public QuestionRecord createQuestion(final Context ctx, final String id, final String content) {
        ChaincodeStub stub = ctx.getStub();
        QuestionRecord questionRecord = JSON.parseObject(content, QuestionRecord.class);
        String jsonString = JSON.toJSONString(questionRecord);
        stub.putStringState("question_record_" + id, jsonString);
        stub.setEvent("createQuestion", jsonString.getBytes());
        return questionRecord;
    }

    /**
     * 根据竞赛||作业id查询是否存在竞赛||作业
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean contestExists(final Context ctx, final String id) {
        ChaincodeStub stub = ctx.getStub();
        String content = stub.getStringState("contest_record_" + id);

        return (content != null && !content.isEmpty());
    }

    /**
     * 根据竞赛||作业id查询竞赛作业相关信息
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public ContestRecord readContestRecordByCtid(final Context ctx, final String id) {

        ChaincodeStub stub = ctx.getStub();
        String stringState = stub.getStringState("contest_record_" + id);

        if (stringState == null || stringState.isEmpty()) {
            String errorMessage = String.format("竞赛||作业 %s 不存在", "contest_record_" + id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage);
        }
        return JSON.parseObject(stringState, ContestRecord.class);
    }

    /**
     * 创建竞赛
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public ContestRecord createContest(final Context ctx, final String id, final String content) {
        ChaincodeStub stub = ctx.getStub();
        ContestRecord contestRecord = JSON.parseObject(content, ContestRecord.class);
        String jsonString = JSON.toJSONString(contestRecord);
        stub.putStringState("contest_record_" + id, jsonString);
        stub.setEvent("createContest", jsonString.getBytes());
        return contestRecord;
    }

    /**
     * 根据学生学工号查询学生是否存在
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean studentExists(final Context ctx, final String susername) {
        ChaincodeStub stub = ctx.getStub();
        String content = stub.getStringState("student_record_" + susername);

        return (content != null && !content.isEmpty());
    }

    /**
     * 根据学生学工号查询学生
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public StudentRecord readStudentRecordBySusername(final Context ctx, final String susername) {
        ChaincodeStub stub = ctx.getStub();
        String stringState = stub.getStringState("student_record_" + susername);

        if (stringState == null || stringState.isEmpty()) {
            String errorMessage = String.format("学生 %s 不存在", "student_record_" + susername);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage);
        }
        return JSON.parseObject(stringState, StudentRecord.class);
    }

    /**
     * 创建学生
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public StudentRecord createStudent(final Context ctx, final String susername, final String content) {
        ChaincodeStub stub = ctx.getStub();
        StudentRecord studentRecord = JSON.parseObject(content, StudentRecord.class);
        String jsonString = JSON.toJSONString(studentRecord);
        stub.putStringState("student_record_" + susername, jsonString);
        stub.setEvent("createStudent", jsonString.getBytes());
        return studentRecord;
    }

    /**
     * 查询做题记录信息
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public SubmitHistory readSubmitHistoryByShid(final Context ctx, final int id) {
        ChaincodeStub stub = ctx.getStub();
        String stringState = stub.getStringState("submit_history_" + id);

        if (stringState == null || stringState.isEmpty()) {
            String errorMessage = String.format("记录 %s 不存在", "student_record_" + id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage);
        }
        return JSON.parseObject(stringState, SubmitHistory.class);
    }

    /**
     * 查询做题记录信息,若未找到则返回null
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public SubmitHistory readSubmitHistoryByShidReturnNull(final Context ctx, final int id) {
        ChaincodeStub stub = ctx.getStub();
        String stringState = stub.getStringState("submit_history_" + id);

        if (stringState == null || stringState.isEmpty()) {
            String errorMessage = String.format("记录 %s 不存在", "student_record_" + id);
            System.out.println(errorMessage);
            return null;
        }
        return JSON.parseObject(stringState, SubmitHistory.class);
    }

    /**
     * 学生做题,提交做题记录
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public SubmitHistory addSubmitHistory(final Context ctx, final int id, final String content) {
        //此处应有id检测
        ChaincodeStub stub = ctx.getStub();
        SubmitHistory submitHistory = JSON.parseObject(content, SubmitHistory.class);
        String jsonString = JSON.toJSONString(submitHistory);
        stub.putStringState("submit_history_" + id, jsonString);
        stub.setEvent("addSubmitHistory", jsonString.getBytes());
        return submitHistory;
    }

    /**
     * 教师提供时间段,计算成绩
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String scoring(final Context ctx, final String content) {
        ChaincodeStub stub = ctx.getStub();

        JSONObject contentObject = JSON.parseObject(content);
        //获取开始时间和结束时间，只统计这段时间内的做题记录
        String start = contentObject.getString("start");
        String end = contentObject.getString("end");
        //Statistics of history in time period
        ArrayList<SubmitHistory> historyArrayList = new ArrayList<>();
        JSONObject studentHistoryList = new JSONObject();
        JSONObject contestHistoryList = new JSONObject();
        //获取所有做题提交记录
        final String startKey = "submit_history_0";
        final String endKey = "submit_history_9999999";
        QueryResultsIterator<KeyValue> results = stub.getStateByRange(startKey, endKey);
        for (KeyValue result : results) {
            SubmitHistory temp = JSON.parseObject(result.getStringValue(), SubmitHistory.class);
            if (inDate(start, end, temp.getStime())) {
                historyArrayList.add(temp);
            }
        }
        //Get all student information
        for (SubmitHistory t : historyArrayList) {
            JSONObject te = new JSONObject();
            te.put("contestScore", 0);
            te.put("score", 0);
            te.put("qidList", new JSONObject());
            studentHistoryList.put(t.getSusername(), te);
        }
        //Get records of all students
        try {
            for (SubmitHistory t : historyArrayList) {
                if (t.getCtid() != 0) {
                    continue;
                }
                JSONObject stu = studentHistoryList.getJSONObject(t.getSusername());
                int accept = t.getAccept();
                String qid = String.valueOf(t.getQid());
                if (stu.getJSONObject("qidList").getInteger(qid) == null) {
                    stu.getJSONObject("qidList").put(String.valueOf(t.getQid()), accept);
                }
                if (accept == 1) {
                    stu.getJSONObject("qidList").put(String.valueOf(t.getQid()), accept);
                }
            }
        } catch (Exception e) {
            throw new ChaincodeException(" 257" + e.getMessage());
        }
        //Get the scores of all students answering questions independently at ordinary times
        try {
            for (String student : studentHistoryList.keySet()) {
                JSONObject stu = studentHistoryList.getJSONObject(student);
                JSONObject qidList = stu.getJSONObject("qidList");
                for (String qid : qidList.keySet()) {
                    Integer accept = qidList.getInteger(qid);
                    if (accept == 0) {
                        continue;
                    }
                    QuestionRecord questionRecord = readQuestionRecordByQid(ctx, qid);
                    int qdifficulty = questionRecord.getQdifficulty();
                    Integer score = stu.getInteger("score");
                    stu.put("score", score + 5 * (qdifficulty + 1));
                }
            }
        } catch (Exception e) {
            throw new ChaincodeException("276" + e.getMessage());
        }
        //Go through all competitions and divide the records according to Ctid and susername
        try {
            for (SubmitHistory t : historyArrayList) {
                if (t.getCtid() == 0) {
                    continue;
                }
                if (contestHistoryList.getJSONObject(String.valueOf(t.getCtid())) == null) {
                    contestHistoryList.put(String.valueOf(t.getCtid()), new JSONObject());
                }
                if (contestHistoryList.getJSONObject(String.valueOf(t.getCtid())).getJSONObject(t.getSusername()) == null) {
                    JSONObject temp = new JSONObject();
                    temp.put("totalAccept", 0);
                    temp.put("acceptQidList", new JSONObject());
                    contestHistoryList.getJSONObject(String.valueOf(t.getCtid())).put(t.getSusername(), temp);
                }
                JSONObject info = contestHistoryList.getJSONObject(String.valueOf(t.getCtid())).getJSONObject(t.getSusername());
                if (t.getAccept() == 1) {
                    info.getJSONObject("acceptQidList").put(String.valueOf(t.getQid()), 1);
                }
            }
        } catch (Exception e) {
            throw new ChaincodeException("299" + e.getMessage());
        }
        //Traverse all competition records and synthesize students' competition conditions
        for (String ctid : contestHistoryList.keySet()) {
            JSONObject contestInfo = contestHistoryList.getJSONObject(ctid);
            for (String susername : contestInfo.keySet()) {
                JSONObject stu = contestInfo.getJSONObject(susername);
                stu.put("totalAccept", stu.getJSONObject("acceptQidList").keySet().size());
            }
        }
        //Traverse all competitions and add competition results to students
        for (String ctid : contestHistoryList.keySet()) {
            JSONObject contestInfo = contestHistoryList.getJSONObject(ctid);
            for (String susername : contestInfo.keySet()) {
                Integer totalAccept = contestInfo.getJSONObject(susername).getInteger("totalAccept");
                Integer contestScore = studentHistoryList.getJSONObject(susername).getInteger("contestScore");
                studentHistoryList.getJSONObject(susername).put("contestScore", contestScore + totalAccept * 70);
            }
        }
        return studentHistoryList.toJSONString();
    }

    private boolean inDate(final String start, final String end, final String now) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate;
        Date nowDate;
        Date endDate;
        try {
            startDate = ft.parse(start.replace('T', ' '));
            nowDate = ft.parse(now.replace('T', ' '));
            endDate = ft.parse(end.replace('T', ' '));
            return startDate.getTime() <= nowDate.getTime() && nowDate.getTime() <= endDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;

    }
}

