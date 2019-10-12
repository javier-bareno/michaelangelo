#include "colors.inc"     
 
 background { color White }    
 
 
 
  /**********************************************
 ** Camera and lights                              
 **********************************************/         
 
#declare camdist = 2000;
                                                   
camera {   
        
        location <0, 0, camdist>
        look_at <0, 0, 0>
}    


light_source { <2000, 00, 1.5*camdist> color White}
light_source { <-2000, 00, 1.5*camdist> color White}   
//light_source { <000, 00, 1.5*camdist> color White}                                                  
//light_source { <-600, 00, -camdist> color White}
//light_source { <600, 00, -camdist> color White}


 /**********************************************
 ** Macros for drawing atoms
 **********************************************/  
 
#declare RTiN = 60;  

 #macro putAtom_Ti1_Ti(X,Y,Z)
 sphere{<X,Y,Z>,RTiN 
 texture{
   pigment{color Grey} 
   finish { phong 0.7 }
  }}  
#end   

#macro putAtom_N1_N(X,Y,Z)
 sphere{<X,Y,Z>,RTiN 
 
 texture { 
   pigment{color Blue} 
   finish { phong 0.7 }
  }} 
#end

#macro putAtom_Hf_Hf(X,Y,Z)
 sphere{<X,Y,Z>,RTiN 
 texture { 
   pigment{color Green} 
   finish { phong 0.7 }
  }}  
#end 

#macro putAtom_N2_N(X,Y,Z)
 sphere{<X,Y,Z>,RTiN 
 texture { 
   pigment{color Blue} 
   finish { phong 0.7 }
  }}
#end


union {

        #include "HfN_001_out.txt"
        
        // ad rotate and anim code here  
        
        translate <-500, -500, -560>
        rotate <0, 0, 180> 
        
        
        #local ang = clock * 90;
        rotate <-ang, 0, 0> 
        
}
