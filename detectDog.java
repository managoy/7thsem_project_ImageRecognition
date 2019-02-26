
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.Ml;
import org.opencv.ml.SVM;
import org.opencv.ml.TrainData;
import org.opencv.objdetect.HOGDescriptor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SUYOG
 */
public class detectDog {
    
    HOGDescriptor hogDesc = new HOGDescriptor(new Size(128,64), new Size(8, 8), new Size(4,4), new Size(4,4), 9);
    
    MatOfFloat detector = new MatOfFloat();
//     Mat[] positives = new Mat[50];
//        Mat[] negatives = new Mat[50];
        MatOfFloat descriptors = new MatOfFloat();
        
        
    public detectDog(){
//         for (int i = 0; i < positives.length; i++) {
//            
//            
//            positives[i] = imread("dog/dog"+i+".jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
//            Imgproc.resize(positives[i],positives[i],new Size(128,64));
//        }
//        
//        
//        for (int i = 0; i < negatives.length; i++) {
//            negatives[i] = imread("dog/n"+i+".jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
//             Imgproc.resize(negatives[i],negatives[i],new Size(128,64));
//        }
Mat pos=imread("dog/dog0.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
Imgproc.resize(pos,pos,new Size(128,64));
        
    hogDesc.compute(pos,descriptors);
     int featureSize = descriptors.rows();
//        int numberofsamples = positives.length + negatives.length;
//        
//        
//        Mat samples= new Mat(numberofsamples, featureSize, CvType.CV_32FC1);
//        
//         for(int i =0;i<featureSize;i++)
//         samples.put(0, i, descriptors.get(i,0)[0]);
//         
//         
//          for(int j =1;j<positives.length;j++){
//            hogDesc.compute(positives[j] ,descriptors);
//        
//            for (int i = 0; i < featureSize; i++) 
//            
//        samples.put(j, i, descriptors.get(i,0)[0]);
//       
//        }
////          for(int k=0;k<positives.length;k++)
////            System.out.println(descriptors.row(k));
//        for(int j =1;j<negatives.length;j++){
//            hogDesc.compute(negatives[j] ,descriptors);
//        for (int i = 0; i < featureSize; i++)        
//        samples.put(j+positives.length, i, descriptors.get(i,0)[0]);
//        }
//        
//         Mat labels = new Mat(numberofsamples, 1, CvType.CV_32SC1);
//       labels.put(0, positives.length, 1);
//         labels.put(positives.length,numberofsamples,-1);
//      
//        labels.rowRange(0, positives.length).setTo(new Scalar(1));
//        
//        
//        
//        labels.rowRange(positives.length,numberofsamples).setTo(new Scalar(-1));
//        
//        SVM svm = SVM.create();
//        svm.setType(SVM.C_SVC);
//        svm.setKernel(SVM.LINEAR);
//        //SVM svm = SVM.load("flowers.yml");
//        
//        
//       for(int i=0;i<labels.rows();i++){
//            System.out.println("Label"+i+":"+labels.row(i).dump());
//       }
//        TrainData trainingData;
//        
//        
//        
//       
//        trainingData = TrainData.create(samples, Ml.ROW_SAMPLE, labels);
//        
//        
//        svm.train(trainingData);
//        
//      svm.save("dog.yml");
SVM svm= SVM.load("dog.yml");
      Mat queries = new Mat(1, featureSize, CvType.CV_32FC1);
      
       
        
        Mat fcimage= imread(new GUI().path.toString(),Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        Imgproc.resize(fcimage,fcimage,new Size(128, 64));
       
      
        hogDesc.compute(fcimage, descriptors);
        
        for(int i =0;i<featureSize;i++)
            queries.put(0, i, descriptors.get(i,0)[0]);
         
        Mat predictions = new Mat();
        
        
        System.out.println("Predicting");
       
        svm.predict(queries,predictions,0);
      
       
      
      for(int i=0;i<1;i++){
           
           System.out.println(predictions.get(i, 0)[0]);
          String b1 =((predictions.get(i, 0)[0]<0.0)? "Not a Dog":"Is a Dog");
            
           
            new GUI().message.setText(b1);
            //System.out.println(value);
            //new GUI().message.setText("checking");
        
      }
        
        
        
    
    }
    
}
