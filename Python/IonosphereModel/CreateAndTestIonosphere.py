import pandas as ps
import numpy as np 
from matplotlib import pyplot as plt 
import keras 
from keras.models import Sequential
from keras.layers import Dense
from keras.layers import LSTM
from keras.layers import Dropout
import os

URL = "Test2005-160.csv"

os.system("java -jar createData.jar "+URL+" 2005 160")

X_test = ps.read_csv(URL)
Y_test = X_test.iloc[:,-1]
X_test = X_test.iloc[:,0:6]
X_test = X_test.values.reshape(-1, 1, 6)
Y_test = Y_test.values.reshape(-1, 1,1)

model = keras.models.load_model('(2-1)model.h5')#3-5
model.summary()
model.evaluate(X_test, Y_test,batch_size=5120)

Y_pred = model.predict(X_test)
MSE = (Y_pred[:,:,:]*750-Y_test[:,:,:]*750)*(Y_pred[:,:,:]*750-Y_test[:,:,:]*750)
m = np.sqrt(np.sum(MSE) / len(Y_test[:,0,0]))
print("M: ",m)

min =  0
max = -1

Size = range(len(Y_pred[min:max,0,0]))

plt.figure()
plt.plot(Size, Y_pred[min:max,0,0]*750+750, 'b', label='Prediction')
plt.title('TEC Values(Prediction)')
plt.legend()

plt.figure()
plt.plot(Size, Y_test[min:max,0,0]*750+750, 'g', label='Real')
plt.title('TEC Values(Real)')
plt.legend()

plt.figure()
plt.plot(Size, (Y_pred[min:max,0,0]-Y_test[min:max,0,0])*750, 'r', label='Prediction - Real )')
plt.title('TEC Values(Error)')
plt.legend()

plt.figure()
plt.plot(Size, np.sqrt((Y_pred[min:max,0,0]-Y_test[min:max,0,0])*(Y_pred[min:max,0,0]-Y_test[min:max,0,0]))*750, 'r', label='sqrt( (Prediction - Real)^2 )')
plt.title('Training and validation loss')
plt.legend()

plt.show()

Data = np.concatenate((X_test[:,0,:], Y_pred[:,0,:]),axis=1)
np.savetxt("Output.csv", Data , delimiter=",")
os.system("java -jar createMap.jar "+URL+" 2.0 0")