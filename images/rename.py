import os

img_index = 1
img_real_index = 1
for img in os.listdir('7-45'):
	os.rename('7-45/img (' + str(img_real_index) + ').jpg','7-45/img(' + str(img_index) + ').jpg')
	img_index += 1
	img_real_index += 1

