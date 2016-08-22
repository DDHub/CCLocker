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
     * @param result
     * @param action
     */
    void onExecuteDone(ActionResult result, IAction action);

    void onExecuteCancel(IAction action);

}
