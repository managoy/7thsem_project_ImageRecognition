
import org.opencv.core.Core;
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
public class detectVehicle{
    HOGDescriptor hogDesc = new HOGDescriptor(new Size(128,64), new Size(8, 8), new Size(4,4), new Size(4,4), 9);
    
    MatOfFloat detector = new MatOfFloat();
//     Mat[] positives = new Mat[50];
//        Mat[] negatives = new Mat[50];
        MatOfFloat descriptors = new MatOfFloat();
        
        
    public detectVehicle(){
//         for (int i = 0; i < positives.length; i++) {
//            
//            
//            positives[i] = imread("car/car"+i+".jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
//            Imgproc.resize(positives[i],positives[i],new Size(128,64));
//        }
//        
//        
//        for (int i = 0; i < negatives.length; i++) {
//            negatives[i] = imread("car/hatch"+i+".jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
//             Imgproc.resize(negatives[i],negatives[i],new Size(128,64));
//        }
Mat pos=imread("car/car0.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
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
//        //System.out.println("Labels:"+labels.dump());
//        labels.rowRange(0, positives.length).setTo(new Scalar(1));
//        
//        
//        
//        labels.rowRange(positives.length,numberofsamples).setTo(new Scalar(-1));
//        
//        SVM svm = SVM.create();
//        svm.setType(SVM.C_SVC);
//        svm.setKernel(SVM.LINEAR);
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
        
//        svm.save("car.csv");
//        svm.save("car.xml");

        //svm.save("finalcar.yml");
        SVM svm =SVM.load("finalcar.yml");
    Mat queries = new Mat(1, featureSize, CvType.CV_32FC1);
      
        Mat [] Samples = new Mat[1];
        
//        for (int i = 0; i < Samples.length; i++) {
//            
//            
//            //Samples[i] = imread("test"+i+".jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
//           Samples[i] = imread("testr3.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
//          
//          Imgproc.resize(Samples[i],Samples[i],new Size(128,64));
//        }
        
        Mat fcimage= imread(new GUI().path.toString(),Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        Imgproc.resize(fcimage,fcimage,new Size(128, 64));
       
        
        //hogDesc.compute(Samples[0], descriptors);
        hogDesc.compute(fcimage, descriptors);
        //System.out.println(descriptors);
        for(int i =0;i<featureSize;i++)
            queries.put(0, i, descriptors.get(i,0)[0]);
         //hogDesc.compute(Samples[1], descriptors);
        
//        for(int i =0;i<featureSize;i++)
//            queries.put(1, i, descriptors.get(i,0)[0]);
//        hogDesc.compute(Samples[2], descriptors);
//        
//        for(int i =0;i<featureSize;i++)
//            queries.put(2, i, descriptors.get(i,0)[0]);
//         hogDesc.compute(Samples[3], descriptors);
//        
//        for(int i =0;i<featureSize;i++)
//            queries.put(3, i, descriptors.get(i,0)[0]);
        Mat predictions = new Mat();
        
        
        System.out.println("Predicting");
        
      svm.predict(queries,predictions,0);
       
        //System.out.println(predictions.dump());
      
      for(int i=0;i<1;i++){
           
            //System.out.println("Query:"+i+":"+((predictions.get(i, 0)[0]<0.0)? "Negative":"Positive"));
            System.out.println(predictions.get(i, 0)[0]);
            String b1 =((predictions.get(i, 0)[0]<0.0)? "Not a Car":"Is a Car");
            
            //System.out.println(b1);
            new GUI().message.setText(b1);
            
            
        
      }
        
        
        
    
    }
    
    
}
