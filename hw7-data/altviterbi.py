e = [l.strip().split("\t") for l in open('hmm-emit-update.txt').readlines()]
emit = {}
for l in e:
	vals = {}
	for x in l[1:]:
		vals[x.split(":")[0]] = float(x.split(":")[-1])
	emit[l[0]] = vals

string = [l.strip() for l in open('cleaned-hmm-decode.txt').readlines()]
seq = []
truth = []
vowels = ['A','E','I','O','U','Y',' ']
for i, c in enumerate(string[0]):
	if c in vowels:
		truth.append('V')
	else:
		truth.append('C')
	v_prob = emit['V'][c]
	c_prob = emit['C'][c]
	if v_prob > c_prob:
		seq.append('V')
	else:
		seq.append('C')

print(truth)
print(seq)
s = len(truth)
correct = 0.0
for i in range(len(seq)):
	if seq[i] == truth[i]: correct += 1

print("Accuracy: " + str(correct/s))