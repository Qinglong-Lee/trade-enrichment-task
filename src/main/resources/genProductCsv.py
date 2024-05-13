# execute cmd first: pip install pandas
import pandas as pd

print("Processing...")

df = pd.read_csv("product.csv"
                 , names = ["product_id", "product_name"], skiprows = [0])


df1 = df.copy()
# replace your count here
for x in range(0, 500):
    df = pd.concat([df, df1], ignore_index = True)

df.to_csv('largeSetProducts.csv', index = False)

print("Finished!")
