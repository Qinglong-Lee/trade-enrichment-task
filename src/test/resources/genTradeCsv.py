# execute cmd first: pip install pandas
import pandas as pd

print("Processing...")

df = pd.read_csv("trade.csv"
                 , names = ["date", "product_id", "currency", "price"], skiprows = [0])

df1 = df.copy()
# replace your count here
for x in range(0, 1000000):
    df = pd.concat([df, df1], ignore_index = True)

df.to_csv('millionsTrades.csv', index = False)

print("Finished!")
