try:
    from robot.libraries.BuiltIn import BuiltIn
    from robot.libraries.BuiltIn import _Misc
    import robot.api.logger as logger
    from robot.api.deco import keyword
    from datetime import datetime
    from hashlib import sha256
    ROBOT = False
except Exception:
    ROBOT = False

def retrieve_mashery_token(key,password):
    now = datetime.now()
    utcTime = round(datetime.timestamp(now));
    print(utcTime)
    return sha256((key+password+str(utcTime)).encode('utf-8')).hexdigest();
    