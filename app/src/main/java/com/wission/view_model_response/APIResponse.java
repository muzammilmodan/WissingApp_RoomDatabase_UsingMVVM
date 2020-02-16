package com.wission.view_model_response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class APIResponse<T> {

    @Nullable
    public T liveData;

    @Nullable
    public final Throwable error;
    public String strCurrentStatus;
    public String strApiName;
    public int intArrayPosition;

    private APIResponse(String strCurrentStatus, @Nullable T liveData, @Nullable Throwable error, String strApiName, int intArrayPosition) {
        this.strCurrentStatus = strCurrentStatus ;
        this.liveData = liveData;
        this.error = error;
        this.strApiName = strApiName;
        this.intArrayPosition = intArrayPosition;
    }

    public static APIResponse loading(String strCurrentStatus, String strApiName, int intArrayPosition) {
        return new APIResponse(strCurrentStatus, null, null,strApiName,0);
    }

    public static<T> APIResponse success(String strCurrentStatus, @NonNull T liveData, String strApiName, int intArrayPosition) {
        return new APIResponse(strCurrentStatus,liveData, null,strApiName,intArrayPosition);
    }

    public static APIResponse error(String strCurrentStatus, @NonNull Throwable error, String strApiName) {
        return new APIResponse(strCurrentStatus,null, error,strApiName,0);
    }

}
