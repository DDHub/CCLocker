package cc.ddhub.cclocker.smart.action;

/**
 * Created by denzelw on 16/8/8.
 */
public interface IActionCallBack {

    /**
     *
     * @param action action
     * @return will be execute this action
     */
    boolean onPreExecute(IAction action);

    /**
     *
     * @param action
     * @param result
     */
    void onExecuteDone(IAction action, ActionResult result);

    void onExecuteCancel(IAction action);

}
