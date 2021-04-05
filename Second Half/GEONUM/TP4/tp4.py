#------------------------------------------------------
#
#  TP4 : Subdivision curves
#  http://tiborstanko.sk/teaching/geo-num-2017/tp4.html
#  [03-Mar-2017]
#
#------------------------------------------------------
#
#  This file is a part of the course:
#    Geometrie numerique (spring 2017)
#    https://github.com/GeoNumTP/GeoNum2017
#    M1 Informatique
#    UFR IM2AG
#
#  Course lecturer:
#    Georges-Pierre.Bonneau at inria.fr
#
#  Practical part:
#    Tibor.Stanko at inria.fr
#
#  Student:
#    Calvin Massonnet
#
#------------------------------------------------------

import sys, os
import matplotlib.pyplot as plt
import numpy as np

TP = os.path.dirname(os.path.realpath(__file__)) + "/"
DATADIR = filename = TP+"data/"


#-------------------------------------------------
# READDATAPOINTS()
# Read datapoints from a file.
#
# Input
#    filename :  file to be read
#
# Output
#    DataPts  :  d x 2 matrix of control points
#
def ReadDatapoints( filename ) :
    global isClosed
    datafile = open(filename,'r');
    # datapoints
    p, closed = np.fromstring(datafile.readline(),sep=' ',dtype=int)
    isClosed = closed==1
    DataPts = np.fromfile(datafile,count=2*p,sep=' ',dtype=float)
    DataPts = DataPts.reshape(-1,2)
    return DataPts


#-------------------------------------------------
# CHAIKIN()
# Perform one iteration of the corner cutting
# subdivision scheme for a closed polygon.
#
# Input
#    X0 :  n x 2 matrix, represents a polygon
#
# Output
#    X1 :  2*n x 2 matrix, subdivided polygon
#
def Chaikin(X0):
    
    # number of points
    n = X0.shape[0]
    
    # upsample
    if isClosed:
        m = 2 * n
    else:
        m = 2 * (n - 2) + 2
    X1 = np.zeros([m, 2])
    
    # Chaikin's subdivision scheme
    for i in range(m / 2):
        X1[i * 2] = (3.0 / 4.0) * X0[i] + (1.0 / 4.0) * X0[(i + 1) % n]
        X1[i * 2 + 1] = (1.0 / 4.0) * X0[i] + (3.0 / 4.0) * X0[(i + 1) % n]
    
    return X1


#-------------------------------------------------
# CORNERCUTTING()
# Perform one iteration of the corner cutting
# subdivision scheme for a closed polygon.
#
# Input
#    X0 :  n x 2 matrix, represents a polygon
#   a,b :  corner cutting parameters
#
# Output
#    X1 :  2*n x 2 matrix, subdivided polygon
#
def CornerCutting(X0, a, b):
    
    # number of points
    n = X0.shape[0]
    
    # upsample
    if isClosed:
        m = 2 * n
    else:
        m = 2 * (n - 2) + 2
    X1 = np.zeros([m, 2])    
    
    # Corner cutting scheme
    for i in range(m / 2):
        X1[i * 2] = (1 - a) * X0[i] + a * X0[(i + 1) % n]
        X1[i * 2 + 1] = (1 - b) * X0[i] + b * X0[(i + 1) % n]
    
    return X1


#-------------------------------------------------
# FOUTPOINT()
# Perform one iteration of the four-point
# subdivision scheme for a closed polygon.
#
# Input
#    X0 :  n x 2 matrix, represents a polygon
#     w :  tension parameter (generalized four-point only)
#
# Output
#    X1 :  2*n x 2 matrix, subdivided polygon
#
def FourPoint(X0, w):
    
    # number of points
    n = X0.shape[0]
    
    # upsample
    if isClosed:
        m = 2 * n
    else:
        m = 2 * (n - 2) + 2
    X1 = np.zeros([m, 2])
    
    # Four-point scheme
    for i in range(m / 2):
        X1[i * 2] = X0[i]
        #X1[i * 2 + 1] = (1.0 / 16.0) * (-1 * X0[(i - 1) % n] + 9 * X0[i] + 9 * X0[(i + 1) % n] + -1 * X0[(i + 2) % n])
        # Generalised version:
        X1[i * 2 + 1] = (-w * X0[(i - 1) % n] + (w + 0.5) * X0[i] + (w + 0.5) * X0[(i + 1) % n] - w * X0[(i + 2) % n])
    
    return X1


#-------------------------------------------------
if __name__ == "__main__":
    
    # helper function for getting scheme name from ID
    def fullname(x):
        return {
            "CH" : "Chaikin",
            "CC" : "Corner cutting",
            "FP" : "Four-point",
        }.get(x,"Invalid")

    ###############################
    ## arg 1 : data name 
    ###############################
    if len(sys.argv) > 1 :
        dataname = sys.argv[1]
    else :
        dataname = "simple" # [simple,infinity]
    ###############################
    ## arg 2 : subdivision scheme
    ###############################
    if len(sys.argv) > 2 :
        scheme = sys.argv[2]
    else :
        scheme = "CH"
    if fullname(scheme) == "Invalid" :
        print " error :  invalid scheme " + scheme
        print "          should be CH, CC or FP"
        sys.exit(0)
    ###############################
    ## arg 3 : depth of subdivision
    ###############################
    if len(sys.argv) > 3 :
        depth = int(sys.argv[3])
    else :
        depth = 3
    
    # output : scheme name and subdivision depth
    print " "+fullname(scheme)
    print " depth = " + str(depth)
    
    # filename
    filename = DATADIR + dataname + ".data"
    
    # check if valid datafile
    if not os.path.isfile(filename) :
        print " error :  invalid dataname '" + dataname + "'"
        print " usage :  python tp4.py  [data=simple,infinity,bone,bunny]  [scheme=CH,CC,FP]  [depth=3]"
        
    else :
        
        ## -- Corner cutting
        a = 0.2
        b = 0.8
        ## -- Generalized four-point
        w = 0.05
        
        ##
        # Experiment with various parameters.
        #
        #a = 0.5
        #b = 0.5
        #
        #a = -0.1
        #b = 1.1
        #
        #a = 0.8
        #b = 0.2
        #
        #a = 0.75
        #b = 0.25
        #
        #w = 0.0
        #w = 0.025
        #w = 0.075
        #w = 0.1
        #w = 0.15
        #w = 1.0
        ##
        
        # read datapoints
        DataPts = ReadDatapoints(filename)
        
        # init subdivided curve
        SubPts = DataPts
        
        # iterative refinement
        for iteration in range(depth) :
            
            # Chaikin
            if scheme == "CH" :
                SubPts = Chaikin(SubPts)
                
            # Corner cutting
            elif scheme == "CC" :
                SubPts = CornerCutting(SubPts, a, b)
            
            # Four-point
            else :
                SubPts = FourPoint(SubPts, w)
        
        # set axes with equal proportions
        plt.axis('equal')
        
        # clear plot
        plt.cla()
        
        # plot coarse polygon
        if isClosed:
            plt.fill(DataPts[:, 0], DataPts[:, 1], edgecolor=.33*np.ones(3), linewidth=1, linestyle='--', fill=False)
        else:
            plt.plot(DataPts[:, 0], DataPts[:, 1], color=.33*np.ones(3), linewidth=1, linestyle='--')
        
        if scheme == "CC":
            colour = 'tab:orange'
        elif scheme == "FP":
            colour = 'tab:green'
        else:
            colour = 'tab:blue'
        # plot subdivided polygon
        if isClosed:
            plt.fill(SubPts[:, 0], SubPts[:, 1], edgecolor=colour, linewidth=2, linestyle='-', fill=False)
        else:
            plt.plot(SubPts[:, 0], SubPts[:, 1], color=colour, linewidth=2)
        
        # titles
        plt.gcf().canvas.set_window_title('TP4 Subdivision curves')
        plt.title(dataname+': scheme=' + fullname(scheme) + ', depth=' + str(depth))
        
        # Save the render as png image in data/
        if scheme == "CC":
            plt.savefig(DATADIR + dataname + "_" + str(depth) + "_" + scheme.lower() + "_" + str(a) + "_" + str(b) + ".png")
        elif scheme == "FP":
            plt.savefig(DATADIR + dataname + "_" + str(depth) + "_" + scheme.lower() + "_" + str(w) + ".png")
        else:
            plt.savefig(DATADIR + dataname + "_" + str(depth) + "_" + scheme.lower() + ".png")
        
        # render
        plt.show()
