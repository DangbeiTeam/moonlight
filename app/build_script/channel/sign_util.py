# coding=utf-8
import subprocess

file_name = "/learadScreenSaver_v1.0_signed"


def signed_apk(workspace, origin_file):
    out_put_file = origin_file[0:origin_file.rindex("/")] + file_name + ".apk"
    path = workspace + "/app/build_script/channel/system_sign/"
    commond = "java -jar {path}signapk.jar {path}platform.x509.pem {path}platform.pk8 {origin_file} {out_put_file}".format(
        path=path, origin_file=origin_file, out_put_file=out_put_file)
    subprocess.call(commond, shell=True)
    return out_put_file


def get_output_file_name(origin_file, channel):
    return origin_file[0:origin_file.rindex(".")] + "_" + channel + ".apk"
