package com.szcgc.comm.message;

/**
 * @Author liaohong
 * @create 2020/8/11 17:53
 */
public interface IProjectSender {

    void projectAction(int accountId, int projectId, String caseName, String opinion, String remarks, String taskId, String extra);

    void projectBehaviour(int accountId, int projectId, String param);

    //void projectFile(int accountId, int projectId, String param);

    default void projectAction(int accountId, int projectId, String caseName, String taskId) {
        projectAction(accountId, projectId, caseName, "", "", taskId, "");
    }
}
