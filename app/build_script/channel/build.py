import os
import subprocess
from optparse import OptionParser
import hashlib
import sign_util
from shutil import copyfile


# import qrcode


def start_generate(archive_name, number, channels, spec, walle_channel_jar):
    for channel in channels:
        generate_internal(archive_name, number, channel.strip(), spec, walle_channel_jar)


def generate_internal(origin_name, number, channel, spec, walle_channel_jar):
    # tmp_apk_base = origin_name[
    #                0:origin_name.rindex('.')] + "_bn" + number + "_[" + channel + "]" + spec

    # tmp_apk = tmp_apk_base + ".apk"
    tmp_apk = sign_util.get_output_file_name(origin_name, channel)

    command_channel = "java -jar -Dfile.encoding=UTF-8 {walle_channel_jar} put -c {channel} -e ci_build_number={number} {input} {output}".format(
        channel=channel, number=number, input=origin_name, output=tmp_apk,
        walle_channel_jar=walle_channel_jar
    )
    subprocess.call(command_channel, shell=True)

    command_show = "java -jar walle-cli-all.jar show {inputapk}".format(inputapk=tmp_apk)
    subprocess.call(command_show, shell=True)

    # md5_hex = generate_md5(tmp_apk)
    # os.rename(tmp_apk, tmp_apk_base + "_" + md5_hex[0:8] + ".apk")


def generate_md5(file_path):
    with open(file_path, 'rb') as f:
        md5obj = hashlib.md5()
        md5obj.update(f.read())
        md5_hex = md5obj.hexdigest()
        print_format2("File(" + file_path + ") --MD5--> " + md5_hex)
        print_format2("File(" + file_path + ") --SIZE--> " + str(os.path.getsize(file_path)))
        return md5_hex


def parse_options():
    parser = OptionParser()
    parser.add_option('-o', '--origin_apk_dir', dest='origin_apk_dir',
                      help="Specify origin apk path")
    parser.add_option('-n', '--number', dest='number', default="unknown",
                      help="Specify build number.")
    parser.add_option('-c', '--channels', dest='channels',
                      help="Specify channels. eg: test,dev,dangbei,haqu")
    parser.add_option('-s', '--stuff', dest='spec', default="", help="Specify stuff.")

    parser.add_option('-w', '--walle_channel_jar', dest='walle_channel_jar', default="",
                      help="walle channel jar location.")

    parser.add_option('-p', '--workspace', dest='workspace', default="",
                      help="project location.")

    (options, args) = parser.parse_args()

    print "[build.py]options: %s, args: %s" % (options, args)
    return options


def print_format(content):
    print "#########################################################"
    print "# -----> {content}".format(content=content)
    print "#########################################################"


def print_format2(content):
    print "+-------------------------------------------------------+"
    print "| -----> {content}".format(content=content)
    print "+-------------------------------------------------------+"


# def generate_file(temp_path):
#     for filename in os.listdir(temp_path):
#         if os.path.isdir(filename):
#             generate_file(filename)
#             print filename + " is folder"
#         elif filename.endswith(".apk"):
#             print "[APK CHANNELS]Start to generate channel package based on: " + filename
#             if not temp_path.endswith("/"):
#                 temp_path += "/"
#             start_generate(temp_path + filename, number, channels, spec, walle_channel_jar)
#     print "[APK CHANNELS]Channel package generation is complete."


if __name__ == '__main__':
    options = parse_options()

    path = options.origin_apk_dir
    number = options.number
    channels = options.channels.split(',')
    spec = options.spec
    walle_channel_jar = options.walle_channel_jar
    workspace = options.workspace

    print_format("APK CHANNELS")
    for root, dirs, files in os.walk(path):
        for file in files:
            filePath = os.path.join(root, file)
            if filePath.endswith(".apk"):
                print "[APK CHANNELS]Start to generate channel package based on: " + filePath
                filePath = sign_util.signed_apk(workspace, filePath)
                # start_generate(filePath, number, channels, spec, walle_channel_jar)

                # qr = qrcode.QRCode(
                #     version=1,
                #     error_correction=qrcode.constants.ERROR_CORRECT_L,
                #     box_size=10,
                #     border=4,
                # )
                # qr.add_data('wangjie')
                # qr.make(fit=True)
                #
                # img = qr.make_image()
                # img.save('wangjie.png')

                # python build.py -o /Users/wangjie/work/python/project/channel_v2/ -n 33 -c -w walle-cli-all.jar test,dev,dangbei,haqu
                # python build_script/channel/build.py -o /Users/wangjie/work/vcs_projects/dangbei/Haqu_android/Haqu/app/build/outputs/apk/ -n 33 -w walle-cli-all.jar -c dev,test,wangjie,dangbei,haqu
