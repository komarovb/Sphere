/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cglast;
import javafx.geometry.Point3D; 
import javafx.scene.Group; 
import javafx.scene.shape.MeshView; 
import javafx.scene.shape.TriangleMesh; 
 
/**
 * 
 * @author Sean 
 */ 
public class Sphere extends Group { 
    public static MeshView CreateSphere(double radius,int triangles) { 

        TriangleMesh mesh = new TriangleMesh(); 
        double phimin=0;
        double phimax=6.28;
        double phi = phimin; 
        double theta; 
        double thetamin= -3.14;
        double thetamax= 2;
        
        for (int i = 0; i < triangles + 1; i++) { 
            theta = thetamin; 
            for (int j = 0; j < triangles + 1; j++) { 
                Point3D p3D = new Point3D((float) (radius * Math.cos(theta) * Math.sin(phi)), 
                        (float) (radius * Math.cos(theta) * Math.cos(phi)), 
                        (float) (radius * Math.sin(theta))); 
                mesh.getPoints().addAll(new Float(p3D.getX()), new Float(p3D.getY()), new Float(p3D.getZ())); 
                theta += (thetamax - thetamin) / triangles; 
            } 
            phi += (phimax - phimin) / triangles; 
        } 
        mesh.getTexCoords().addAll(0, 0); 
        for (int i = 0; i < triangles; i++) { 
            int multiplier = (i * triangles) + i; 
            for (int j = multiplier; j < triangles + multiplier; j++) { 
                mesh.getFaces().addAll(j, 0, j + 1, 0, j + triangles + 1, 0);
                mesh.getFaces().addAll(j + triangles + 1, 0, j + 1, 0, j + triangles + 2, 0);
            }           
            for (int j = triangles + multiplier; j > multiplier; j--) { 
                mesh.getFaces().addAll(j, 0, j - 1, 0, j + triangles + 1, 0); 
                mesh.getFaces().addAll(j - 1, 0, j + triangles, 0, j + triangles + 1, 0);
            } 
        } 
        MeshView meshView = new MeshView(mesh); 
        
        return meshView;
    } 
 
}