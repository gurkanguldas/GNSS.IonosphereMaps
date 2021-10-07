import keras
from keras.models import Sequential
from keras.layers import Dense
from keras.layers import LSTM
from keras.layers import Dropout
import dask.dataframe as dd
import pandas as pd
import numpy as np
from matplotlib import pyplot as plt
import gc

Epochs = 5
PartitionEpochs = 10

X_validation = pd.read_csv("MixNormalized_Ionosphere_Dataset_Validation.csv")
Y_validation = X_validation.iloc[:,-1]
X_validation = X_validation.iloc[:,0:6]
X_validation = X_validation.values.reshape(-1, 1, 6)
Y_validation = Y_validation.values.reshape(-1, 1,1)
 
X_test = pd.read_csv("MixNormalized_Ionosphere_Dataset_Test.csv")
Y_test = X_test.iloc[:,-1]
X_test = X_test.iloc[:,0:6]
X_test = X_test.values.reshape(-1, 1, 6)
Y_test = Y_test.values.reshape(-1, 1,1)

model = Sequential()
model.add(LSTM(256, input_shape=(1, 6), return_sequences=True, activation='relu'))
model.add(Dense(256, activation='relu'))
model.add(Dense(256, activation='relu'))
model.add(Dense(256, activation='relu'))
model.add(Dense(256, activation='relu'))
model.add(Dense(256, activation='relu'))
model.add(Dense(256, activation='relu'))
model.add(Dense(256, activation='relu'))
model.add(Dense(256, activation='relu'))
model.add(Dense(256, activation='relu'))
model.add(Dense(256, activation='relu'))
model.add(Dense(256, activation='relu'))
model.add(Dense(256, activation='relu'))
model.add(Dense(256, activation='relu'))
model.add(Dense(256, activation='relu'))
model.add(Dense(256, activation='relu'))
model.add(LSTM(64, input_shape=(1, 6), return_sequences=True, activation='relu'))
model.add(LSTM(32, input_shape=(1, 6), return_sequences=True, activation='relu'))
model.add(LSTM(16, input_shape=(1, 6), return_sequences=True, activation='relu'))
model.add(Dense(1, activation='tanh'))
model.compile(optimizer = 'adam', loss='mse' ,metrics=['mae'])
model.build(input_shape=(1,1,6))
model.summary()

Dask_train = dd.read_csv("MixNormalized_Ionosphere_Dataset_Train.csv",blocksize=15e8)

for a in range(Epochs):
    for i in range(Dask_train.npartitions):
        
        X_train = Dask_train.get_partition(i).compute(scheduler='synchronous')
        Y_train = X_train.iloc[:,-1]
        X_train = X_train.iloc[:,0:6]
        
        X_train = X_train.values.reshape(-1, 1, 6)
        Y_train = Y_train.values.reshape(-1, 1, 1)
        
        print(len(X_train[:,0,0]))
        model.fit(X_train, Y_train, epochs=PartitionEpochs-1 , batch_size=5120, validation_data=(X_validation,Y_validation),verbose=1)
        
        print("("+str(a+1)+"-"+str(i+1)+")model.h5 icin train-validation sonucları...")
        model.fit(X_train, Y_train, epochs=1 , batch_size=5120, validation_data=(X_validation,Y_validation),verbose=1)
        
        model.save("("+str(a+1)+"-"+str(i+1)+")model.h5")
        
        del X_train
        del Y_train
        gc.collect()
        
        print("("+str(a+1)+"-"+str(i+1)+")model.h5 icin test sonucları...")
        model.evaluate(X_test, Y_test,batch_size=5120)
    
        gc.collect()