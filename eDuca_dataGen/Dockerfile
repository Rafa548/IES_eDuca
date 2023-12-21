FROM python:3.9

ADD GenerateData.py /

RUN pip install kafka-python

RUN pip install requests

CMD ["python3", "GenerateData.py" ]