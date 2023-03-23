package com.szcgc.finance.service.impl;

import com.szcgc.comm.exception.BaseException;
import com.szcgc.comm.service.BaseService;
import com.szcgc.finance.model.ExamineFinanceInfo;
import com.szcgc.finance.model.ExamineInfo;
import com.szcgc.finance.model.base.BalanceSheet;
import com.szcgc.finance.model.base.IncomeStatement;
import com.szcgc.finance.repository.ExamineFinanceRepository;
import com.szcgc.finance.repository.ExamineRepository;
import com.szcgc.finance.repository.base.BalanceSheetRepository;
import com.szcgc.finance.repository.base.IncomeStatementRepository;
import com.szcgc.finance.service.IExamineService;
import com.szcgc.finance.vo.PostAnalysis;
import com.szcgc.finance.vo.PostAnalysisVo;
import com.szcgc.project.feign.IProjectClient;
import com.szcgc.project.model.ProjectInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author liaohong
 * @create 2022/9/17 16:41
 */
@Service
public class ExamineService extends BaseService<ExamineRepository, ExamineInfo, Integer> implements IExamineService {

    @Autowired
    BalanceSheetRepository balanceSheetRepository;

    @Autowired
    IncomeStatementRepository incomeStatementRepository;

    @Autowired
    IProjectClient iProjectClient;

    @Autowired
    ExamineFinanceRepository examineFinanceRepository;

    @Override
    public int enroll(int projectId) {
        //修改提交状态
        return repository.update4Enroll(projectId,ExamineInfo.STATUS_ENROLLED, ExamineInfo.STATUS_INIT);
    }

    @Override
    public int audit(int projectId, int accountId) {
        //修改审核状态
        return repository.update4Audit(projectId,ExamineInfo.STATUS_AUDITED,accountId, LocalDateTime.now(),ExamineInfo.STATUS_ENROLLED);
    }

    @Override
    public List<ExamineInfo> findByProjectId(int projectId) {
        return repository.findByProjectId(projectId);
    }

    @Override
    public List<PostAnalysisVo>  examineFinanceDetail(int projectId,String financialReport) {
        ProjectInfo byId = iProjectClient.findById(projectId);
        if (null == byId) {
            throw new BaseException("项目不存在");
        }
        //返回VO
        List<PostAnalysisVo> postAnalysisVos = getPostAnalysisVos(projectId,byId.getCustomerId(),financialReport);
        return postAnalysisVos;
    }

    private List<PostAnalysisVo> getPostAnalysisVos(int projectId,int custId,String currentDate) {
        int yearTerm = Integer.parseInt(currentDate.substring(0, 4));
        String lastDate =String.valueOf(yearTerm-1)+"-"+currentDate.substring(5, 7);
        BalanceSheet balanceSheetCurrent =balanceSheetRepository.findTop1ByProjectIdAndCustomerIdAndDateOrderByUpdateAtDesc(projectId,custId,currentDate);
        BalanceSheet balanceSheetLast = balanceSheetRepository.findTop1ByProjectIdAndCustomerIdAndDateOrderByUpdateAtDesc(projectId, custId, lastDate);
        IncomeStatement incomeStatementCurrent = incomeStatementRepository.findTop1ByProjectIdAndCustomerIdAndDateOrderByUpdateAtDesc(projectId, custId, currentDate);
        IncomeStatement incomeStatementLast = incomeStatementRepository.findTop1ByProjectIdAndCustomerIdAndDateOrderByUpdateAtDesc(projectId,custId,lastDate);
        List<PostAnalysisVo> postAnalysisVos = new ArrayList<>();
        initFinanceData(balanceSheetCurrent, balanceSheetLast, incomeStatementCurrent, incomeStatementLast, postAnalysisVos);
        return postAnalysisVos;
    }

    private void initFinanceData(BalanceSheet balanceSheetCurrent, BalanceSheet balanceSheetLast, IncomeStatement incomeStatementCurrent, IncomeStatement incomeStatementLast, List<PostAnalysisVo> postAnalysisVos) {
        PostAnalysisVo vo1 = new PostAnalysisVo(PostAnalysis.CCE.getCnName(),null == balanceSheetLast || null == balanceSheetLast.getA01()?"0":balanceSheetLast.getA01().toString(),
                null == balanceSheetCurrent || null == balanceSheetCurrent.getA01()?"0":balanceSheetCurrent.getA01().toString());
        postAnalysisVos.add(vo1);
        PostAnalysisVo vo2 = new PostAnalysisVo(PostAnalysis.AR.getCnName(), add(null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA0e(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA0g(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA0i(),
                null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA0k(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA0m(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA0p(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA0r()),
                add(null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA0e(),null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA0g(),null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA0i(),
                        null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA0k(),null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA0m(),null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA0p(),null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA0r()));
        postAnalysisVos.add(vo2);
        PostAnalysisVo vo3 = new PostAnalysisVo(PostAnalysis.FD.getCnName(), null == balanceSheetLast || null == balanceSheetLast.getA0t()?"0":balanceSheetLast.getA0t().toString(),
                null == balanceSheetCurrent || null == balanceSheetCurrent.getA0t()?"0":balanceSheetCurrent.getA0t().toString());
        postAnalysisVos.add(vo3);
        PostAnalysisVo vo4 = new PostAnalysisVo(PostAnalysis.DE.getCnName(), add(null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA0v(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA2k()),
                add(null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA0v(),null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA2k()));
        postAnalysisVos.add(vo4);
        PostAnalysisVo vo5 = new PostAnalysisVo(PostAnalysis.LTI.getCnName(),add(null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA10(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA11(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA14(),
                null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA16()),add(null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA10(),null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA11(),
                null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA14(),null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA16()));
        postAnalysisVos.add(vo5);
        PostAnalysisVo vo6 = new PostAnalysisVo(PostAnalysis.FA.getCnName(), add(null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA13(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA1i(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA1k(),
                null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA1p()),
                add(null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA13(),null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA1i(),null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA1k(),null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA1p()));
        postAnalysisVos.add(vo6);
        PostAnalysisVo vo7 = new PostAnalysisVo(PostAnalysis.TA.getCnName(), null == balanceSheetLast || null == balanceSheetLast.getA2z()?"0":balanceSheetLast.getA2z().toString(),
                null == balanceSheetCurrent || null == balanceSheetCurrent.getA2z()?"0":balanceSheetCurrent.getA2z().toString());
        postAnalysisVos.add(vo7);
        PostAnalysisVo vo8 = new PostAnalysisVo(PostAnalysis.LSL.getCnName(), add(null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA31(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA33(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA41()),
                add(null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA31(),null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA33(),null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA41()));
        postAnalysisVos.add(vo8);
        PostAnalysisVo vo9 = new PostAnalysisVo(PostAnalysis.PAY.getCnName(), add(null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA35(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA37(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA39(),
                null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA3b(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA3f(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA3h(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA3j(),
                null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA3r(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA3t()),add(null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA35(),null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA37(),
                null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA39(),null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA3b(),null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA3f(),null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA3h(),
                null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA3j(),null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA3r(),null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA3t()));
        postAnalysisVos.add(vo9);
        PostAnalysisVo vo10 = new PostAnalysisVo(PostAnalysis.AT.getCnName(),null == balanceSheetLast || null == balanceSheetLast.getA3d()?"0":balanceSheetLast.getA3d().toString(),
                null == balanceSheetCurrent || null == balanceSheetCurrent.getA3d()?"0":balanceSheetCurrent.getA3d().toString());
        postAnalysisVos.add(vo10);
        PostAnalysisVo vo11 = new PostAnalysisVo(PostAnalysis.CUC.getCnName(),null == balanceSheetLast || null == balanceSheetLast.getA51()?"0":balanceSheetLast.getA51().toString(),
                null == balanceSheetCurrent || null == balanceSheetCurrent.getA51()?"0":balanceSheetCurrent.getA51().toString());
        postAnalysisVos.add(vo11);
        PostAnalysisVo vo12 = new PostAnalysisVo(PostAnalysis.PIS.getCnName(),null == balanceSheetLast || null == balanceSheetLast.getA53()?"0":balanceSheetLast.getA53().toString(),
                null == balanceSheetCurrent || null == balanceSheetCurrent.getA53()?"0":balanceSheetCurrent.getA53().toString());
        postAnalysisVos.add(vo12);
        PostAnalysisVo vo13 = new PostAnalysisVo(PostAnalysis.RE.getCnName(), add(null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA57(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA5b()),
                add(null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA57(),null == balanceSheetCurrent?BigDecimal.ZERO:balanceSheetCurrent.getA5b()));
        postAnalysisVos.add(vo13);
        PostAnalysisVo vo14 = new PostAnalysisVo(PostAnalysis.LAE.getCnName(),null == balanceSheetLast || null == balanceSheetLast.getA5x()?"0":balanceSheetLast.getA5x().toString(),
                null == balanceSheetCurrent || null == balanceSheetCurrent.getA5x()?"0":balanceSheetCurrent.getA5x().toString());
        postAnalysisVos.add(vo14);
        PostAnalysisVo vo15 = new PostAnalysisVo(PostAnalysis.OI.getCnName(),null == incomeStatementLast || null == incomeStatementLast.getB01()?"0":incomeStatementLast.getB01().toString(),
                null == incomeStatementCurrent || null == incomeStatementCurrent.getB01()?"0":incomeStatementCurrent.getB01().toString());
        postAnalysisVos.add(vo15);
        PostAnalysisVo vo16 = new PostAnalysisVo(PostAnalysis.CAT.getCnName(),add(null == incomeStatementLast?BigDecimal.ZERO:incomeStatementLast.getB02(),null == incomeStatementLast?BigDecimal.ZERO:incomeStatementLast.getB03()),
                add(null == incomeStatementCurrent?BigDecimal.ZERO:incomeStatementCurrent.getB02(),null == incomeStatementCurrent?BigDecimal.ZERO:incomeStatementCurrent.getB03()));
        postAnalysisVos.add(vo16);
        PostAnalysisVo vo17 = new PostAnalysisVo(PostAnalysis.PE.getCnName(),add(null == incomeStatementLast?BigDecimal.ZERO:incomeStatementLast.getB0a(),null == incomeStatementLast?BigDecimal.ZERO:incomeStatementLast.getB0c(),null == incomeStatementLast?BigDecimal.ZERO:incomeStatementLast.getB0e()),
                add(null == incomeStatementCurrent?BigDecimal.ZERO:incomeStatementCurrent.getB0a(),null == incomeStatementCurrent?BigDecimal.ZERO:incomeStatementCurrent.getB0c(),null == incomeStatementCurrent?BigDecimal.ZERO:incomeStatementCurrent.getB0e()));
        postAnalysisVos.add(vo17);
        PostAnalysisVo vo18 = new PostAnalysisVo(PostAnalysis.IFI.getCnName(),null == incomeStatementLast || null == incomeStatementLast.getB0k()?"0":incomeStatementLast.getB0k().toString(),
                null == incomeStatementCurrent || null == incomeStatementCurrent.getB0k()?"0":incomeStatementCurrent.getB0k().toString());
        postAnalysisVos.add(vo18);
        PostAnalysisVo vo19 = new PostAnalysisVo(PostAnalysis.IT.getCnName(),null == incomeStatementLast || null == incomeStatementLast.getB2c()?"0":incomeStatementLast.getB2c().toString(),
                null == incomeStatementCurrent || null == incomeStatementCurrent.getB2c()?"0":incomeStatementCurrent.getB2c().toString());
        postAnalysisVos.add(vo19);
        PostAnalysisVo vo20 = new PostAnalysisVo(PostAnalysis.NeP.getCnName(),null == incomeStatementLast || null == incomeStatementLast.getB3a()?"0":incomeStatementLast.getB3a().toString(),
                null == incomeStatementCurrent || null == incomeStatementCurrent.getB3a()?"0":incomeStatementCurrent.getB3a().toString());
        postAnalysisVos.add(vo20);
    }

    private static String add(BigDecimal... addend){
        BigDecimal result = BigDecimal.ZERO;
        if(addend.length==0){
            return "0";
        }
        for (BigDecimal addVal : addend){
            if(null != addVal){
                result =result.add(addVal);
            }
        }
        return result.toString();
    }

    @Override
    public void examineFinanceAdd(int projectId, String financialReport, int accountId) {
        ProjectInfo byId = iProjectClient.findById(projectId);
        int yearTerm = Integer.parseInt(financialReport.substring(0, 4));
        String lastDate =String.valueOf(yearTerm-1)+"-"+financialReport.substring(5, 7);
        BalanceSheet balanceSheetCurrent =balanceSheetRepository.findTop1ByProjectIdAndCustomerIdAndDateOrderByUpdateAtDesc(projectId,byId.getCustomerId(),financialReport);
        BalanceSheet balanceSheetLast = balanceSheetRepository.findTop1ByProjectIdAndCustomerIdAndDateOrderByUpdateAtDesc(projectId, byId.getCustomerId(), lastDate);
        IncomeStatement incomeStatementCurrent = incomeStatementRepository.findTop1ByProjectIdAndCustomerIdAndDateOrderByUpdateAtDesc(projectId, byId.getCustomerId(), financialReport);
        IncomeStatement incomeStatementLast = incomeStatementRepository.findTop1ByProjectIdAndCustomerIdAndDateOrderByUpdateAtDesc(projectId,byId.getCustomerId(),lastDate);
        saveExamineFinanceInfo(projectId, accountId, byId, lastDate, balanceSheetLast, incomeStatementLast);
        saveExamineFinanceInfo(projectId, accountId, byId, financialReport, balanceSheetCurrent, incomeStatementCurrent);
    }

    private void saveExamineFinanceInfo(int projectId, int accountId, ProjectInfo byId, String lastDate, BalanceSheet balanceSheetLast, IncomeStatement incomeStatementLast) {
        ExamineFinanceInfo financeInfo = new ExamineFinanceInfo();
        financeInfo.setProjectId(projectId);
        financeInfo.setFinancialReport(lastDate);
        financeInfo.setCustomerId(byId.getCustomerId());
        financeInfo.setCreateBy(accountId);
        financeInfo.setCce(null == balanceSheetLast || null == balanceSheetLast.getA01()?"0":balanceSheetLast.getA01().toString());
        financeInfo.setAr(add(null == balanceSheetLast? BigDecimal.ZERO:balanceSheetLast.getA0e(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA0g(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA0i(),
                null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA0k(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA0m(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA0p(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA0r()));
        financeInfo.setFd(null == balanceSheetLast || null == balanceSheetLast.getA0t()?"0":balanceSheetLast.getA0t().toString());
        financeInfo.setDe(add(null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA0v(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA2k()));
        financeInfo.setLti(add(null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA10(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA11(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA14(),
                null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA16()));
        financeInfo.setFa(add(null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA13(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA1i(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA1k(),
                null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA1p()));
        financeInfo.setTa(null == balanceSheetLast || null == balanceSheetLast.getA2z()?"0":balanceSheetLast.getA2z().toString());
        financeInfo.setLsl(add(null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA31(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA33(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA41()));
        financeInfo.setPay(add(null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA35(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA37(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA39(),
                null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA3b(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA3f(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA3h(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA3j(),
                null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA3r(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA3t()));
        financeInfo.setAt(null == balanceSheetLast || null == balanceSheetLast.getA3d()?"0":balanceSheetLast.getA3d().toString());
        financeInfo.setCuc(null == balanceSheetLast || null == balanceSheetLast.getA51()?"0":balanceSheetLast.getA51().toString());
        financeInfo.setPis(null == balanceSheetLast || null == balanceSheetLast.getA53()?"0":balanceSheetLast.getA53().toString());
        financeInfo.setRe(add(null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA57(),null == balanceSheetLast?BigDecimal.ZERO:balanceSheetLast.getA5b()));
        financeInfo.setLae(null == balanceSheetLast || null == balanceSheetLast.getA5x()?"0":balanceSheetLast.getA5x().toString());
        financeInfo.setOi(null == incomeStatementLast || null == incomeStatementLast.getB01()?"0":incomeStatementLast.getB01().toString());
        financeInfo.setCat(add(null == incomeStatementLast?BigDecimal.ZERO:incomeStatementLast.getB02(),null == incomeStatementLast?BigDecimal.ZERO:incomeStatementLast.getB03()));
        financeInfo.setPe(add(null == incomeStatementLast?BigDecimal.ZERO:incomeStatementLast.getB0a(),null == incomeStatementLast?BigDecimal.ZERO:incomeStatementLast.getB0c(),null == incomeStatementLast?BigDecimal.ZERO:incomeStatementLast.getB0e()));
        financeInfo.setIfi(null == incomeStatementLast || null == incomeStatementLast.getB0k()?"0":incomeStatementLast.getB0k().toString());
        financeInfo.setIt(null == incomeStatementLast || null == incomeStatementLast.getB2c()?"0":incomeStatementLast.getB2c().toString());
        financeInfo.setNep(null == incomeStatementLast || null == incomeStatementLast.getB3a()?"0":incomeStatementLast.getB3a().toString());
        examineFinanceRepository.save(financeInfo);
    }

}
