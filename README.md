# S-AES
信息安全导论第二次小组作业，包括源代码、测试结果文档、用户指南和开发手册。

# 用户指南

## 一、简介

本用户指南介绍了一个基于简化高级加密标准（S-AES）的加密解密程序。本程序支持基础加密解密操作、多重加密模式、以及分组链模式（CBC）。用户可以通过图形界面（GUI）实现交互式加密解密功能，以下是界面展示：
![image](https://github.com/user-attachments/assets/92fad99d-c5b6-421c-bf1d-579db60119eb)


## 二、安装指南

### 环境要求
- 确保您的计算机安装了Java环境（推荐Java 8或更高版本）。

### 下载程序
- 您可以下载我们的软件包，并将其解压缩到您的计算机上。

### 启动程序
- 打开IDEA，导航到软件包所在的目录，并运行`GUI.java`。

## 三、功能介绍

### 基础功能

#### 加密
- **输入**：16位二进制字符串的明文和密钥
- **输出**：16位二进制字符串的密文
- **操作**：
  - 打开程序，在加密界面中输入16位的明文和密钥，点击“加密”按钮，即可获得对应的16位密文。
    ![image](https://github.com/user-attachments/assets/e6b29413-d783-4386-8830-a63b097c7c52)
    ![image](https://github.com/user-attachments/assets/c837b277-67ea-4e8b-ad01-3fd94a497904)



#### 解密
- **输入**：16位二进制字符串的密文和密钥
- **输出**：16位二进制字符串的明文
- **操作**：
  - 在解密界面输入16位的密文和密钥，点击“解密”按钮，将显示解密后的16位明文。
    ![image](https://github.com/user-attachments/assets/7c96def5-5fba-4375-9892-cee8bfa9edea)
    ![image](https://github.com/user-attachments/assets/1215af2a-2aac-4244-ae40-ed6c0df16eb3)



### 扩展功能

#### ASCII编码支持
- 程序支持输入ASCII编码字符串，将其分组为2字节并进行加密，输出为ASCII编码的字符串（可能为乱码）。
  ![image](https://github.com/user-attachments/assets/e9579d86-4689-461d-91ca-230a3ecf91dc)


#### 双重加密
- **密钥长度**：32位
- **操作**：
  - 用户输入32位的密钥和16位的明文。
  - 加密过程：使用密钥的前16位进行首次加密，随后使用后16位再次加密。
![image](https://github.com/user-attachments/assets/fe65e3d2-2dd2-4bda-b6ee-4c1451713a2d)
![image](https://github.com/user-attachments/assets/548fee88-255f-47f3-928e-019daa42c867)
  - 解密过程：反向操作，即使用后16位进行第一次解密，再使用前16位解密结果。
  ![image](https://github.com/user-attachments/assets/b1dfaeb8-978a-4031-815c-576c7be5843e)
![image](https://github.com/user-attachments/assets/0d451765-7a2a-4e35-9a89-514a6e059399)



#### 三重加密
- **密钥长度**：48位（16位密钥的三重组合）
- **操作**：
  - 用户输入48位的密钥和16位明文。
  - 加密过程：依次使用每16位密钥进行三次加密。
    ![image](https://github.com/user-attachments/assets/45171493-3b76-44de-92f6-9764c8e3b738)
![image](https://github.com/user-attachments/assets/0d17dc45-9d16-4891-803f-c3b0eb3972ec)

  - 解密过程：按相反顺序逐步解密。
![image](https://github.com/user-attachments/assets/e172bb38-04e8-425b-8fb9-a832c4a5bc47)
![image](https://github.com/user-attachments/assets/0d607b16-a652-4fe0-a715-e55cf4fe4649)

### 安全功能

#### 中间相遇攻击
- 程序实现了中间相遇攻击，用于在已知明文和密文对的情况下，通过穷举密钥找出加密使用的密钥组合。
![image](https://github.com/user-attachments/assets/92a67c43-8781-4b15-9d5d-7021eb10e4f3)

### 工作模式 - CBC（密码分组链模式）

#### 加密
- **输入**：较长的明文、16位的密钥和初始向量（IV）
- **操作**：
  - 输入后点击“CBC加密”按钮。
![image](https://github.com/user-attachments/assets/3753a44a-9410-42e1-b079-bcab29b26481)

#### 解密
- **输入**：较长的密文、16位的密钥和初始向量（IV）
- **操作**：
  - 输入后点击“CBC解密”按钮。
![image](https://github.com/user-attachments/assets/177f66ae-2daa-41e0-9847-be300c796d62)

#### 密文篡改
- 对密文进行篡改，使用相同的密钥和IV对篡改后的密文进行解密，解密结果将产生明显差异，方便进行数据完整性验证。
![image](https://github.com/user-attachments/assets/e2812cd3-b89a-442f-9fad-d325b6dcbe82)

## 四、常见问题

- **输入错误**：如果输入的明文或密钥不是16比特，程序将提示错误信息。请确保输入正确格式的数据。
  ![image](https://github.com/user-attachments/assets/eea6cffe-b2d0-41ce-a364-b761c44d881c)
  ![image](https://github.com/user-attachments/assets/9cb0e362-5a76-4248-92d8-0d153dbdbf3d)


- **空输入**：如果明文为空，则无法进行加密或解密操作。请至少输入一些数据。
![image](https://github.com/user-attachments/assets/e73c0b87-49f7-49be-8d22-98c1035cc419)

## 五、探索更多

- 本指南提供了使用该程序的基础知识。您可以进一步阅读源代码以了解实现细节，或者尝试修改代码来探索不同的加密方案。

希望这份指南能够帮助您顺利地使用我们的加密解密工具。如有任何疑问或反馈，请随时联系我们。


# 开发手册

## 一、概述

简化AES（S-AES）是一种对AES加密算法的简化模型，它适用于学习密码学基本概念。该实现基于《密码编码学与网络安全：原理与实践》（第8版）中附录D的内容，旨在展示AES的核心操作，如字节替换、行移位、列混淆和密钥扩展等。此README包含了算法简介、算法和GUI的接口文档及使用示例，以便于对项目进行封装与调用。

## 二、算法简介

### 加密过程

S-AES加密过程包括初始轮密钥添加和两轮主要操作。

- **初始轮密钥添加**：
  - 将16位的输入数据与第一个轮密钥K0进行XOR（异或）运算，以增加初始数据的复杂性。

- **第一轮和第二轮操作**：
  - **字节替换**：
    - 使用一个4x4的S盒替换每个4位的半字节数据。
  - **行移位**：
    - 对行进行循环位移。
  - **列混淆**（仅在第一轮执行）：
    - 将每一列中的数据通过GF(2^4)上的矩阵乘法混合。
  - **轮密钥添加**：
    - 每一轮结束时，将经过处理的结果与相应的轮密钥（K1或K2）进行XOR运算，以进一步加密数据。

- **最终轮**：
  - 第二轮加密过程中，跳过“列混淆”步骤，仅执行字节替换、行移位和轮密钥添加。

### 解密过程

解密过程步骤与加密过程相反，包括逆字节替换、逆行位移和逆列混淆。

### 密钥扩展

通过 g 函数和轮常量扩展初始密钥。将16位密钥分为左右两部分，每一部分各8位。记原始密钥为第0个密钥。函数g的步骤，将第i-1个密钥的右半部分（8位）执行完上述步骤后得到g(第i-1个密钥的右半部分)，将其与第i-1个密钥的左半部分（8位）进行异或得到第i个密钥的左半部分。
第i个密钥的右半部分由第i个密钥的左半部分与第i-1个密钥的右半部分进行异或得到。

## 三、算法接口说明（SAES.java）

1. **`keyExpansion(String keyStr)`**：
   - 实现密钥扩展，将输入的16位二进制密钥扩展为多个轮密钥，用于后续加密和解密轮次中的密钥。

2. **`substitute(int[] state)`**：
   - 进行字节替换操作，使用S盒替换输入状态中的字节，以实现加密过程中的字节混淆。

3. **`invSubstitute(int[] state)`**：
   - 实现字节还原操作，在解密中通过逆S盒替换状态中的字节，将字节还原到加密前的状态。

4. **`addRoundKey(int[] state, int[] roundKey)`**：
   - 轮密钥加操作，将输入状态和当前轮的密钥进行按位异或，实现加密轮次中的密钥加运算。

5. **`shiftRows(int[] state)`**：
   - 行移位操作，对状态中的行进行左移位处理，用于加密中的行移位步骤，增强混淆效果。

6. **`mixColumns(int[] state)`**：
   - 列混合操作，对输入状态中的列进行多项式乘法，增加列间字节的依赖性，以实现扩散效果。

7. **`invMixColumns(int[] state)`**：
   - 逆列混合操作，用于解密过程中的列混合还原，将状态还原到列混合前的状态。

8. **`encrypt(String plaintext, String keyStr)`**：
   - 加密主流程，对输入明文进行多轮处理，依次执行字节替换、行移位、列混合和轮密钥加，生成最终密文。

9. **`decrypt(String ciphertext, String keyStr)`**：
   - 解密主流程，通过逆行移位、逆字节替换、逆列混合和轮密钥加，将密文还原为原始明文。

10. **`asciiToBinary(String ascii)`** 和 **`binaryToAscii(String binary)`**：
    - 字符转换函数，分别将ASCII字符串转换为二进制字符串，或将二进制字符串还原为ASCII，支持字符到二进制的转换。

11. **`doubleEncrypt(String plaintext, String key)`** 和 **`doubleDecrypt(String ciphertext, String key)`**：
    - 实现双重加密与解密，通过两轮连续加密或解密（使用不同密钥），以提高加密的安全性。

12. **`meetInTheMiddleAttack(String plaintext, String ciphertext)`**：
    - 中间相遇攻击实现，通过记录明文和密文的中间状态并进行比对，尝试破解双重加密的密钥。

## 四、GUI接口说明（GUI.java）

- 1.**`public GUI()`**
  - 功能: 初始化主窗口和选项卡面板，包含不同加密解密界面的标签。

- **主要组件**

  - `JFrame`：主窗口，标题为 "S-AES 加密/解密"。
  - `JTabbedPane`：用于组织不同的加密解密界面。

- 2.**`private JPanel createSingleEncryptionPanel()`**
  - 功能: 创建单重加密界面。
  - 组件:
    - 输入明文、密钥和密文的文本框。
    - ASCII 明文、密钥和密文的文本框。
    - 加密和解密按钮。
  - 操作流程:
    - 用户输入明文和密钥（均为16位二进制字符串）或ASCII字符（2字节），点击加密按钮进行加密，结果显示在密文框中。
    - 用户输入密文和密钥进行解密，结果显示在明文框中。

- 3.**`private JPanel createDoubleEncryptionPanel()`**
  - 功能: 创建双重加密界面。
  - 组件:
    - 输入明文、密钥和密文的文本框。
    - 加密和解密按钮。
  - 操作流程:
    - 用户输入16位明文和32位密钥，点击加密按钮进行加密。
    - 用户输入密文和密钥进行解密，结果显示在明文框中。

- 4.**`private JPanel createMeetInTheMiddlePanel()`**
  - 功能: 创建中间相遇攻击界面。
  - 组件:
    - 输入明文、密文和密钥的文本框。
    - 攻击按钮。
  - 操作流程:
    - 用户输入16位明文和密文，点击攻击按钮进行中间相遇攻击，结果显示在密钥框中。

- 5.**`private JPanel createTripleEncryptionPanel()`**
  - 功能: 创建三重加密界面。
  - 组件:
    - 输入明文和三个密钥的文本框。
    - 密文文本框。
    - 加密和解密按钮。
  - 操作流程:
    - 用户输入16位明文和三个16位密钥，点击加密按钮进行加密。
    - 用户输入密文和三个密钥进行解密，结果显示在明文框中。

- 6.**`private JPanel createCbcEncryptionPanel()`**
  - 功能: 创建 CBC 加密/解密界面。
  - 组件:
    - 输入明文、密钥和初始向量 (IV) 的文本框。
    - 密文和解密后明文的文本框。
    - CBC 加密和解密按钮。
  - 操作流程:
    - 用户输入明文、16位密钥和16位 IV，点击 CBC 加密按钮进行加密。
    - 用户输入密文进行解密，结果显示在解密后明文框中。

## 五、附录

### 术语表

1. **S-AES**：子字节高级加密标准（Substitution-AES），是一种用于加密和解密的对称密钥加密算法，基于AES的结构设计，适合于教育和教学目的。
2. **明文**：未加密的数据，用户输入的原始信息。
3. **密文**：加密后的数据，只有持有密钥的用户才能解密。
4. **密钥**：加密和解密过程中使用的秘密信息。密钥的安全性直接影响到加密系统的安全性。
5. **ASCII**：美国标准信息交换码（American Standard Code for Information Interchange），是一种字符编码标准，用于表示文本中的字符。
6. **IV（初始化向量）**：在某些加密模式（如CBC模式）中使用的随机数，用于确保相同的明文块在每次加密时生成不同的密文。
7. **CBC（密码块链接模式）**：一种加密模式，允许使用相同的密钥加密多个块，但每个块的加密结果与前一个块的密文相关联，增加了安全性。
8. **攻击**：在密码学中，攻击是指试图破坏加密算法安全性的行为。

### 参考文档

《密码编码学于网络安全—原理与实践(第8版)》，附录D：简化AES
