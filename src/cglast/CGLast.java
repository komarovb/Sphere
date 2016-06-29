/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cglast;

import static cglast.Sphere.CreateSphere;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.stage.Stage;

/**
 *
 * @author Borys
 */
public class CGLast extends Application {
    
    final Group root = new Group();
    final Xform world = new Xform();
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final Xform cameraXform = new Xform();
    final Xform cameraXform2 = new Xform();
    final Xform cameraXform3 = new Xform();
    private static final double CAMERA_INITIAL_DISTANCE = -1500;
    private static final double CAMERA_INITIAL_X_ANGLE = 0.0;
    private static final double CAMERA_INITIAL_Y_ANGLE = 50.0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;
    private static final double CONTROL_MULTIPLIER = 0.1;    private static final double SHIFT_MULTIPLIER = 10.0;    private static final double MOUSE_SPEED = 0.1;    private static final double ROTATION_SPEED = 2.0;    private static final double TRACK_SPEED = 0.3;
        
    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;
 
    private void buildCamera() {
        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
 
        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
    }
    private void handleMouse(Scene scene, final Node root) {
 
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent me) {
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent me) {
                mouseOldX = mousePosX;
                mouseOldY = mousePosY;
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseDeltaX = (mousePosX - mouseOldX); 
                mouseDeltaY = (mousePosY - mouseOldY);

               double modifier = 1.0;

               if (me.isControlDown()) {
                    modifier = CONTROL_MULTIPLIER;
                } 
                if (me.isShiftDown()) {
                    modifier = SHIFT_MULTIPLIER;
                }     
                if (me.isPrimaryButtonDown()) {
                    cameraXform.ry.setAngle(cameraXform.ry.getAngle() -
                       mouseDeltaX*MOUSE_SPEED*modifier*ROTATION_SPEED);  // 
                   cameraXform.rx.setAngle(cameraXform.rx.getAngle() +
                       mouseDeltaY*MOUSE_SPEED*modifier*ROTATION_SPEED);  // -
                }
                else if (me.isSecondaryButtonDown()) {
                    double z = camera.getTranslateZ();
                    double newZ = z + mouseDeltaX*MOUSE_SPEED*modifier*10;
                    camera.setTranslateZ(newZ);
                }
                else if (me.isMiddleButtonDown()) {
                   cameraXform2.t.setX(cameraXform2.t.getX() + 
                      mouseDeltaX*MOUSE_SPEED*modifier*TRACK_SPEED);  // -
                   cameraXform2.t.setY(cameraXform2.t.getY() + 
                      mouseDeltaY*MOUSE_SPEED*modifier*TRACK_SPEED);  // -
                }
           }
       });
   } 
    @Override
    public void start(Stage primaryStage) {
 
        System.out.println("start()");

        root.getChildren().add(world);
        root.setDepthTest(DepthTest.ENABLE);

        buildCamera();
        MeshView view = CreateSphere(300,100);
        view.setDrawMode(DrawMode.LINE);
        world.getChildren().add(view);
        
        Scene scene = new Scene(root, 1024, 800, true);
        scene.setFill(Color.LIGHTGRAY);
        handleMouse(scene, world);

        primaryStage.setTitle("CG task 5");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setCamera(camera);
    }
 
    /**
     * The main() method is ignored in correctly deployed JavaFX 
     * application. main() serves only as fallback in case the 
     * application can not be launched through deployment artifacts, 
     * e.g., in IDEs with limited FX support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
