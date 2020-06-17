package com.spellingtrip.example.utils;

import android.app.Activity;

import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;

public class CameraUtils {
    private static int orientationDegree;

    /**
     * 适配相机旋转
     *
     * @param activity
     * @param
     * @param camera
     */
    public static void setCameraDisplayOrientation(Activity activity, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(getDefaultCameraId(), info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        //前置
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        }
        //后置
        else {
            result = (info.orientation - degrees + 360) % 360;
        }
        orientationDegree = result;
        camera.setDisplayOrientation(result);
    }

    private static int getDefaultCameraId(){
        int defaultId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();

        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                defaultId = i;
            }
        }
        if (defaultId == -1) {
            if (numberOfCameras > 0) {
                //没有后置摄像头
                defaultId = 0;
            } else {
                ToastUtil.show("没有摄像头");
            }
        }
        return defaultId;
    }
}
