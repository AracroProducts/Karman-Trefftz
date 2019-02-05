from android.os import Bundle
from android.support.v7.app import AppCompatActivity
from android.widget import ImageView
from android.view import View
from android.graphics import Bitmap, BitmapFactory
from com.aracro.mg.python.KarmanTrefftz import R
from com.aracro.python.KarmanTrefftz import MainActivity
from java import jvoid, Override, static_proxy, jarray
from scipy import interpolate
import numpy as np
import cmath, math
import matplotlib.path as mpltPath
import matplotlib.pyplot as plt
import io

class GraphOutput(static_proxy(AppCompatActivity)):

    @Override(jvoid, [Bundle])
    def onCreate(self, state):
        AppCompatActivity.onCreate(self, state)
        self.setContentView(R.layout.activity_graph_output)
        sharedpreferences = MainActivity.sharedpreferences
        AoA = sharedpreferences.getInt("AoA", 5)
        Arch = sharedpreferences.getInt("Arch", 2)
        TE = sharedpreferences.getInt("TE", 15)
        Mux = (50. - sharedpreferences.getInt("Mux", 45)) / -100.

        # Plotting parameters
        windowSize = 1
        quiverResolution = 0.05
        contourResolution = 0.01
        nContours = 25

        # Geometric parameters
        chord = 1
        teAngle = TE*math.pi/180
        arch = Arch*math.pi/180
        mux = Mux # Should be in negative x direction

        # Derived parameters
        k = 2. - (teAngle / math.pi)
        muy = arch * k
        r = 1.
        b = (1. - (muy)**2)**(1./2.) + mux

        # Normalize inputs with respect to chord
        chordNorm = 2. * k * b / (1. - (1. - (b / math.cos(arch)))**k)
        b = b * chord / chordNorm
        r = r * chord / chordNorm
        mux = mux * chord / chordNorm
        muy = muy * chord / chordNorm
        mu = complex(mux, muy)

        # Flow parameters
        Vfs = 2

        circulation = -4. * math.pi * Vfs * r * math.sin(AoA + math.asin(muy / r))
        #l = -997*Vfs*circulation
        #cl = 2*l/(997*Vfs*Vfs)

        # Method to transform circle into airfoil
        def transformZ(Z, k, b):
            z = k * b * ((Z + b)**k + (Z - b)**k) / ((Z + b)**k - (Z - b)**k)
            return z

        # Method to obtain the velocity field around a circle
        def circleVelField(Vfs, AoA, circulation, Z, mu, r):
            first = complex(Vfs * math.cos(-AoA), Vfs * math.sin(-AoA))
            second = complex(0, circulation) / (2. * math.pi * (Z - mu))
            third = complex(Vfs * r**2 * math.cos(AoA), Vfs * r**2 * math.sin(AoA)) / (Z - mu)**2
            return (first - second - third)

        # Method to obtain the derivative of the transform with respect to the original
        # plane
        def derivativeZ(Z, k, b):
            dz = 4. * k**2 * b**2 * (Z + b)**(k - 1.) * (Z - b)**(k - 1.) / ((Z + b)**k - (Z - b)**k)**2
            if dz == 0:
                dz = 1
            return dz

        # Method to obtain pressure coefficients from velocities
        def cp(velZ, Vfs):
            cp = 1. - (abs(velZ)**2 / Vfs**2)
            return cp

        # Method for inverse transformm from z-plane into original
        def inverseZ(z, k, b):
            # Find kappa
            kappa = 1. / k
            Z = -b * (1. + ((z + (k * b))/(z - (k * b)))**kappa) / (1. - ((z + (k * b))/(z - (k * b)))**kappa)
            return (Z)

        # Checks if a point is inside the original circle
        def pointInCircle(Z, mu, r):
            inside = False
            if (abs(Z - mu) < r * 0.98): # 0.98 to mask just inside the circle to aviod whitespace at the edge of the foil
                inside = True
            return inside

        # Run main
        xFoil = []
        yFoil = []
        xVel = []
        yVel = []
        xCp = []
        yCp = []
        #maxCp = 0
        #minCp = 0
        for theta in range(0, 360):
            X = r * math.cos(theta * math.pi / 180) + mux
            Y = r * math.sin(theta * math.pi / 180) + muy
            Z = complex(X, Y)
            transform = transformZ(Z, k, b)
            #    Z = inverseZ(transform, k, b)
            #    transform = transformZ(Z, k, b)
            xFoil.append(transform.real)
            yFoil.append(transform.imag)
            velZ = circleVelField(Vfs, AoA, circulation, Z, mu, r) / derivativeZ(Z, k, b)
            velX = velZ.real
            velY = -velZ.imag
            xVel.append(velX)
            yVel.append(velY)
            c = cp(velZ, Vfs)
            xCp.append(transform.real)
            yCp.append(c)
            #if(c>maxCp):
            #    maxCp = c
            #if(c<minCp):
            #    minCp = c

        '''
        maxc = 0
        minc = 0
        for i in range(len(xCp) - 1):
            c = (xCp[i+1] - xCp[i])/(yCp[i+1] - yCp[i])
            if(c>maxc):
                maxc = c
            if(c<minc):
                minc = c
        '''

        X = []
        Y = []
        U = []
        V = []
        for y in range(int(-windowSize / quiverResolution), int(1 + (windowSize / quiverResolution))):
            Ui = []
            Vi = []
            X.append(y * quiverResolution)
            Y.append(y * quiverResolution)
            for x in range(int(-windowSize / quiverResolution), int(1 + (windowSize / quiverResolution))):
                z = complex(x * quiverResolution, y * quiverResolution)
                Z = inverseZ(z, k, b)
                velZ = circleVelField(Vfs, AoA, circulation, Z, mu, r) / derivativeZ(Z, k, b)
                velX = velZ.real
                velY = -velZ.imag
                if (pointInCircle(Z, mu, r)):
                    Ui.append(0)
                    Vi.append(0)
                else:
                    Ui.append(velX)
                    Vi.append(velY)
            U.append(Ui)
            V.append(Vi)

        X1 = []
        Y1 = []
        CP = []
        for y in range(int(-windowSize / contourResolution), int(1 + (windowSize / contourResolution))):
            CPi = []
            X1.append(y * contourResolution)
            Y1.append(y * contourResolution)
            for x in range(int(-windowSize / contourResolution), int(1 + (windowSize / contourResolution))):
                z = complex(x * contourResolution, y * contourResolution)
                Z = inverseZ(z, k, b)
                velZ = circleVelField(Vfs, AoA, circulation, Z, mu, r) / derivativeZ(Z, k, b)
                velX = velZ.real
                velY = -velZ.imag
                if (pointInCircle(Z, mu, r)):
                    CPi.append(1) #NaN
                else:
                    CPi.append(cp(velZ, Vfs))
            CP.append(CPi)

        # Figure 1
        fig = plt.figure()
        ax = fig.add_subplot(111)
        CS = ax.contourf(X1, Y1, CP, nContours)
        cbar = fig.colorbar(CS)
        cbar.ax.set_ylabel('Pressure Coefficient')
        q = ax.quiver(X, Y, U, V, units='width', width = 0.001)
        ax.quiverkey(q, X=0.3, Y=1.05, U=5, label='Velocity vector, 5 ms^-1', labelpos='E')
        ax.fill(xFoil, yFoil, color = 'g')
        ax.set_aspect('equal', 'datalim')
        ax.set_xlabel('Real Axis')
        ax.set_ylabel('Imaginary Axis')

        """
        # Figure 2
        fig2 = plt.figure()
        ax2 = fig2.add_subplot(111)
        ax2.plot(xFoil, yFoil, 'k-', label = 'Foil', color = 'g')
        ax2.plot(xCp, yCp, 'k-', label = 'Pressure', color = 'b')
        ax2.set_aspect('equal', 'datalim')
        ax2.set_xlabel('Real Axis')
        ax2.set_ylabel('Imaginary Axis')
        """
        #bitmap = Bitmap.createBitmap
        #plt.savefig(self.findViewById(R.id.imageView).setImageBitmap(), format="png")