import os
import subprocess
from optparse import OptionParser
import ConfigParser

import StringIO


def parse_options():
    parser = OptionParser()
    parser.add_option('-o', '--origin_apk_dir', dest='origin_apk_dir', help="Specify origin apk dir.")
    parser.add_option('-k', '--keystore_properties_file', dest='keystore_properties_file',
                      help="Specify android keystore properties file for sign apk.")
    parser.add_option('-j', '--jiagu360_properties_file', dest='jiagu360_properties_file',
                      help="Specify jiagu360 properties file for jiagu.")

    (options, args) = parser.parse_args()

    print "[build.py]options: %s, args: %s" % (options, args)
    return options


def jiagu(jiagu_360_dir, username, password, apk_file):
    jiagu_360_jar = '{jiagu_360_dir}/jiagu.jar'.format(jiagu_360_dir=jiagu_360_dir)
    # login
    command_login = "java -jar -Dfile.encoding=UTF-8 {jiagu_360_jar} -login {username} {password}".format(
        jiagu_360_jar=jiagu_360_jar, username=username, password=password
    )
    subprocess.call(command_login, shell=True)

    # x86 support
    command_config_x86 = 'java -jar -Dfile.encoding=UTF-8 {jiagu_360_jar} -config -x86'.format(
        jiagu_360_jar=jiagu_360_jar)
    subprocess.call(command_config_x86, shell=True)

    # dest dir
    jiagu_tmp = apk_file[0:apk_file.rindex('/')] + "/jiagu"

    # clear jiaqu dir and create

    if os.path.exists(jiagu_tmp):
        subprocess.call("rm -R {jiagu_tmp}".format(jiagu_tmp=jiagu_tmp), shell=True)

    subprocess.call("mkdir {jiagu_tmp}".format(jiagu_tmp=jiagu_tmp), shell=True)
    subprocess.call("chmod -R 775 {jiagu_tmp}".format(jiagu_tmp=jiagu_tmp), shell=True)

    # jiagu
    command_jiagu = 'java -jar -Dfile.encoding=UTF-8 {jiagu_360_jar} -jiagu {apk_file} {jiagu_tmp}'.format(
        jiagu_360_jar=jiagu_360_jar, apk_file=apk_file, jiagu_tmp=jiagu_tmp)
    subprocess.call(command_jiagu, shell=True)

    result_file = apk_file[0:apk_file.rindex('.')] + "_jiagu.apk"

    for tmp in os.listdir(jiagu_tmp):
        if tmp.endswith(".apk"):
            full_path = jiagu_tmp + "/" + tmp
            os.chmod(full_path, 775)
            os.rename(full_path, result_file)

    subprocess.call("rm -R {jiagu_tmp}".format(jiagu_tmp=jiagu_tmp), shell=True)

    print '[JIAGU]jiagu completed'
    return result_file


def sign_apk(keystore, ks_key_alias, ks_pass, key_pass, signed_apk, origin_apk):
    # config = ConfigParser.RawConfigParser()
    # /Users/wangjie/work/java/security/360jiagubao/jiagu/java/bin/jarsigner
    # http://docs.oracle.com/javase/7/docs/technotes/tools/windows/jarsigner.html

    # command_sign = 'jarsigner -verbose -sigfile CERT -digestalg SHA1 -sigalg MD5withRSA -tsa https://timestamp.geotrust.com/tsa -keystore {keystore} -storepass {storepass} -signedjar {signed_apk} {origin_apk} {keystore_alias}'.format(
    #     keystore=keystore,
    #     storepass=storepass,
    #     signed_apk=signed_apk,
    #     origin_apk=origin_apk,
    #     keystore_alias=keystore_alias
    # )

    command_sign = 'apksigner sign --ks {keystore} --ks-key-alias {ks_key_alias} --ks-pass pass:{ks_pass} --key-pass pass:{key_pass} --out {signed_apk} {origin_apk}'.format(
        keystore=keystore,
        ks_key_alias=ks_key_alias,
        ks_pass=ks_pass,
        key_pass=key_pass,
        signed_apk=signed_apk,
        origin_apk=origin_apk
    )

    subprocess.call(command_sign, shell=True)

    os.remove(origin_apk)

    print '[SIGN]sign completed'


def zip_align(zip_aligned_apk, origin_apk):
    command_zipalign = 'zipalign -f -v 4 {origin_apk} {zip_aligned_apk}'.format(origin_apk=origin_apk,
                                                                                zip_aligned_apk=zip_aligned_apk)
    subprocess.call(command_zipalign, shell=True)

    os.remove(origin_apk)
    print '[ZIP ALIGN]zipaligin completed'


def read_properties_files(file_path):
    config = StringIO.StringIO()
    config.write('[x]\n')
    config.write(open(file_path).read())
    config.seek(0, os.SEEK_SET)
    properties = ConfigParser.ConfigParser()
    properties.readfp(config)
    return properties


def print_format(content):
    print "#########################################################"
    print "# -----> {content}".format(content=content)
    print "#########################################################"


if __name__ == '__main__':
    options = parse_options()

    # keystore properties
    keystore_properties = read_properties_files(options.keystore_properties_file)

    # jiagu360 properties
    jiagu360_properties = read_properties_files(options.jiagu360_properties_file)

    jiagu_360_base_dir = jiagu360_properties.get('x', 'jiagu_360_base_dir')
    jiagu_360_username = jiagu360_properties.get('x', 'jiagu_360_username')
    jiagu_360_password = jiagu360_properties.get('x', 'jiagu_360_password')

    sign_storeFile_release = keystore_properties.get('x', 'sign_storeFile_release')
    sign_storePassword_release = keystore_properties.get('x', 'sign_storePassword_release')
    sign_keyAlias_release = keystore_properties.get('x', 'sign_keyAlias_release')
    sign_keyPassword_release = keystore_properties.get('x', 'sign_keyPassword_release')

    origin_apk_dir = options.origin_apk_dir

    for filename in os.listdir(origin_apk_dir):
        if filename.endswith(".apk"):
            if "_release" not in filename:
                print "[JIAGU]Ignore jiagu based on: " + filename + " because of it is not release package. ---"
                continue

            # jiagu
            print_format("JIAGU")
            print "[JIAGU]Start to jiagu and sign based on: " + filename
            jiagu_apk_file = jiagu(jiagu_360_base_dir, jiagu_360_username, jiagu_360_password,
                                   origin_apk_dir + "/" + filename)

            # zip align
            print_format("ZIP ALIGN")
            print "[ZIP ALIGN]Start to zip align apk: " + jiagu_apk_file
            zip_aligned_file = jiagu_apk_file[0:jiagu_apk_file.rindex('.')] + "_zipaligned.apk"
            zip_align(zip_aligned_file, jiagu_apk_file)

            # sign
            print_format("SIGN")
            print "[SIGN]Start to sign apk: " + zip_aligned_file
            signed_apk_file = zip_aligned_file[0:zip_aligned_file.rindex('.')] + "_signed.apk"
            sign_apk(sign_storeFile_release, sign_keyAlias_release, sign_storePassword_release, sign_keyPassword_release, signed_apk_file, zip_aligned_file)


            # app >> python build_script/jiagu/jiagu.py -o build/outputs/apk -k gradle.properties -j build_script/jiagu/jiagu360.properties
            # project/channel_for_android  >> python ../jiagu360/jiagu360.py -o /Users/wangjie/work/python/project/channel_for_android -k keystore.properties -j jiagu360.properties
